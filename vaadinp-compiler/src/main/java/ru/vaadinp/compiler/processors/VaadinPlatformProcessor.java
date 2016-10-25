package ru.vaadinp.compiler.processors;

import com.sun.tools.javac.code.Symbol;
import freemarker.template.TemplateException;
import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.GenerateVPComponent;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.compiler.JavaSourceGenerator;
import ru.vaadinp.compiler.datamodel.*;
import ru.vaadinp.compiler.datamodel.vaadinpmodule.GenerateVaadinPlatformModuleModel;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.slot.root.RootVPComponent;
import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.PresenterComponent;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Created by oem on 10/21/16.
 */
public class VaadinPlatformProcessor extends AbstractProcessor {

	private Messager messager;
	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;

	private final List<GenerateVPComponentModel> vPComponentModels = new ArrayList<>();
	private final Set<GenerateNestedVPComponentModel> sortedNestedVPComponents = new LinkedHashSet<>();

	private boolean processed = false;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		messager = processingEnv.getMessager();
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		filer = processingEnv.getFiler();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (!processed) {
			final List<GenerateNestedVPComponentModel> nestedVPComponents = new ArrayList<>();

			GenerateNestedVPComponentModel rootVPComponent = null;
			GenerateNestedVPComponentModel appRootVPComponent = null;

			GenerateNestedVPComponentModel defaultPlace = null;
			GenerateNestedVPComponentModel errorPlace = null;
			GenerateNestedVPComponentModel notFoundPlace = null;

			for (Element element : roundEnv.getElementsAnnotatedWith(GenerateVPComponent.class)) {
				final TypeElement presenterElement = (TypeElement) element;

				isValidPrefix(presenterElement);

				final ApiMirror apiMirror = getApi(presenterElement);
				final ViewMirror viewMirror = getView(apiMirror);

				if (isPresenterComponent(presenterElement)) {

					vPComponentModels.add(
							new GenerateVPComponentModel(new AnnotatedPresenterComponent(presenterElement, apiMirror, viewMirror))
					);

				} else if (isNestedPresenter(presenterElement)) {
					final AnnotatedNestedPresenter annotatedNestedPresenter = new AnnotatedNestedPresenter(presenterElement, apiMirror, viewMirror);
					annotatedNestedPresenter.setNestedSlotMirror(getNestedSlot(presenterElement));
					annotatedNestedPresenter.setNameToken(getNameToken(presenterElement));

					final GenerateNestedVPComponentModel parent = new GenerateNestedVPComponentModel(getRevealIn(presenterElement));
					final GenerateNestedVPComponentModel nestedVPComponent = new GenerateNestedVPComponentModel(annotatedNestedPresenter);
					nestedVPComponent.setParent(parent);

					if (parent.getFqn().equals(RootVPComponent.class.getName())) {
						rootVPComponent = parent;
						appRootVPComponent = nestedVPComponent;

						sortedNestedVPComponents.add(parent);
					}

					nestedVPComponents.add(nestedVPComponent);
				}
			}

			Iterator<GenerateNestedVPComponentModel> nestedPresenterIterator = nestedVPComponents.iterator();

			while (nestedVPComponents.size() > 0) {

				if (!nestedPresenterIterator.hasNext()) {
					nestedPresenterIterator = nestedVPComponents.iterator();
				}

				while (nestedPresenterIterator.hasNext()) {

					final GenerateNestedVPComponentModel vpComponent = nestedPresenterIterator.next();

					if (sortedNestedVPComponents.contains(vpComponent.getParent())) {
						nestedPresenterIterator.remove();
						sortedNestedVPComponents.add(vpComponent);
					}
				}
			}

			if (rootVPComponent != null) {
				sortedNestedVPComponents.remove(rootVPComponent);
			}

			for (GenerateVPComponentModel vpComponent : vPComponentModels) {
				try {
					final JavaFileObject simpleVPComponentSource = filer.createSourceFile(vpComponent.getFqn());

					try (Writer writer = simpleVPComponentSource.openWriter()) {
						writer.write(JavaSourceGenerator.generateJavaSource(vpComponent, "SimpleVPComponent.ftl"));
					} catch (TemplateException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			for (GenerateNestedVPComponentModel vpComponent : sortedNestedVPComponents) {
				try {
					final JavaFileObject simpleVPComponentSource = filer.createSourceFile(vpComponent.getFqn());

					try (Writer writer = simpleVPComponentSource.openWriter()) {
						if (vpComponent.getPresenterComponent().getNameToken() == null) {
							writer.write(JavaSourceGenerator.generateJavaSource(vpComponent, "NestedVPComponent.ftl"));
						} else {
							writer.write(JavaSourceGenerator.generateJavaSource(vpComponent, "PlaceVPComponent.ftl"));
						}
					} catch (TemplateException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}



			processed = true;
		}

		return true;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return new LinkedHashSet<String>() {{
			add(GenerateVPComponent.class.getCanonicalName());
			add(RevealIn.class.getCanonicalName());
		}};
	}

	private boolean isPresenterComponent(TypeElement presenter) {
		return typeUtils.isSameType(
			typeUtils
				.asElement(
					presenter.getSuperclass()
				).asType(),
			elementUtils.getTypeElement(
				PresenterComponent
					.class
					.getName()
			).asType()
		);
	}

	private boolean isNestedPresenter(TypeElement presenter) {
		return typeUtils.isSameType(
			typeUtils
				.asElement(
					presenter.getSuperclass()
				).asType(),
			elementUtils.getTypeElement(
				NestedPresenter
					.class
					.getName()
			).asType()
		);
	}

	private void isValidPrefix(TypeElement presenterComponent) {
		if (!presenterComponent.getSimpleName().toString().endsWith("Presenter")) {
			throw new IllegalStateException(
				String.format(
					"Имя %s должно оканчиваться на Presenter",
					presenterComponent
				)
			);
		}
	}

	private ApiMirror getApi(TypeElement presenterComponent) {
		final String elementName = presenterComponent.getSimpleName().toString();
		final String packageName = Symbol.class.cast(presenterComponent).packge().getQualifiedName().toString();
		final String possibleApi = elementName.substring(0, elementName.length() - "Presenter".length());

		final TypeElement api = elementUtils.getTypeElement(packageName + "." + possibleApi);

		if (api == null) {
			throw new IllegalStateException(
				String.format("Api %s не найдено", packageName + "." + possibleApi)
			);
		}

		return new ApiMirror(api);
	}

	private ViewMirror getView(ApiMirror apiMirror) {
		final String possibleViewName = apiMirror
			.getFqn()
			.toString() + "View";

		final TypeElement possibleViewImpl = elementUtils.getTypeElement(possibleViewName);

		if (possibleViewImpl == null) {
			throw new IllegalStateException(
				String.format("View %s не найдено", apiMirror.getPackageName() + "." + possibleViewName)
			);
		}

		return new ViewMirror(possibleViewImpl);
	}

	private NestedSlotMirror getNestedSlot(TypeElement nestedPresenterElement) {
		NestedSlotMirror slot = null;

		for (Element element : elementUtils.getAllMembers(nestedPresenterElement)) {
			if (element.getKind() == ElementKind.FIELD) {
				final VariableElement variableElement = (VariableElement) element;

				if (typeUtils.isSameType(variableElement.asType(), elementUtils.getTypeElement(NestedSlot.class.getName()).asType())) {
					if (!isPublicStaticFinalField(variableElement)) {
						throw new IllegalStateException(
							String.format("%s должен быть public static final", variableElement)
						);
					}

					slot = new NestedSlotMirror(variableElement);

					break;
				}

				break;
			}
		}

		return slot;
	}


	private String getNameToken(TypeElement presenterElement) {
		String nameToken = null;

		for (Element element : elementUtils.getAllMembers(presenterElement)) {
			if (element.getKind() == ElementKind.FIELD && element.getAnnotation(GenerateNameToken.class) != null) {
				final VariableElement nameTokenElement = (VariableElement) element;

				if (!typeUtils.isSameType(nameTokenElement.asType(), elementUtils.getTypeElement(String.class.getName()).asType())) {
					throw new IllegalStateException(
						String.format("%s тип должен быть String", nameTokenElement)
					);
				}

				if (!isPublicStaticFinalField(nameTokenElement)) {
					throw new IllegalStateException(
						String.format("%s должен быть public static final", nameTokenElement)
					);
				}

				nameToken = nameTokenElement.getConstantValue().toString();

				break;
			}
		}

		return nameToken;
	}

	private AnnotatedNestedPresenter getRevealIn(TypeElement presenterElement) {
		AnnotatedNestedPresenter parent = null;

		for (Element element : elementUtils.getAllMembers(presenterElement)) {
			if (element.getKind() == ElementKind.CONSTRUCTOR) {
				final  ExecutableElement executableElement = (ExecutableElement) element;

				for (VariableElement variableElement : executableElement.getParameters()) {
					final RevealIn revealInAnnotation = variableElement.getAnnotation(RevealIn.class);

					if (typeUtils.isSameType(variableElement.asType(), elementUtils.getTypeElement(NestedSlot.class.getName()).asType())) {
						if (revealInAnnotation == null) {
							throw new IllegalStateException(
								String.format("%s забыли аннотацию %s", variableElement, RevealIn.class)
							);
						}

						try {
							revealInAnnotation.value();
						} catch (MirroredTypeException e) {
							final TypeElement parentPresenter = (TypeElement) typeUtils.asElement(e.getTypeMirror());

							final ApiMirror apiMirror = getApi(parentPresenter);
							final ViewMirror viewMirror = getView(apiMirror);

							parent = new AnnotatedNestedPresenter(parentPresenter, apiMirror, viewMirror);
						}
					}
				}
			}
		}
		return parent;
	}

	private boolean isPublicStaticFinalField(VariableElement variableElement) {
		return variableElement.getModifiers().containsAll(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL));
	}

}
