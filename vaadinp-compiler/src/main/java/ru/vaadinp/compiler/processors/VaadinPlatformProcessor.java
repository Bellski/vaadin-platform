package ru.vaadinp.compiler.processors;

import com.sun.source.util.Trees;
import freemarker.template.TemplateException;
import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.GenerateVPComponent;
import ru.vaadinp.compiler.JavaSourceGenerator;
import ru.vaadinp.compiler.datamodel.AnnotatedGenerateVPComponentClass;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.PresenterComponent;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by oem on 10/20/16.
 */
public class VaadinPlatformProcessor extends AbstractProcessor {
	private Messager messager;
	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;

	private Trees trees;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		messager = processingEnv.getMessager();
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		filer = processingEnv.getFiler();

		trees = Trees.instance(processingEnv);
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (Element element : roundEnv.getElementsAnnotatedWith(GenerateVPComponent.class)) {
			final AnnotatedGenerateVPComponentClass vPComponentClass = new AnnotatedGenerateVPComponentClass((TypeElement) element);

			try {
				isValidClass(vPComponentClass);

				final JavaFileObject simpleVPComponentSource = filer.createSourceFile(vPComponentClass.getQualifiedName());


				try (Writer writer = simpleVPComponentSource.openWriter()) {

					if (isPresenterComponent((TypeElement) element)) {
						writer.write(JavaSourceGenerator.generateJavaSource(vPComponentClass, "SimpleVPComponent.ftl"));
					} if (isNestedPresenter((TypeElement) element)) {
						vPComponentClass.setSlotElement(getNestedSlot((TypeElement) element));
						vPComponentClass.setNameTokenElement(getNameToken((TypeElement) element));

						if (vPComponentClass.hasNameToken()) {
							writer.write(JavaSourceGenerator.generateJavaSource(vPComponentClass, "PlaceVPComponent.ftl"));
						} else {
							writer.write(JavaSourceGenerator.generateJavaSource(vPComponentClass, "NestedVPComponent.ftl"));
						}
					}
				} catch (TemplateException e) {
					messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
				}

			} catch (IllegalStateException | IOException e) {
				messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
			}
		}

		return true;
	}


	private void isValidClass(AnnotatedGenerateVPComponentClass vPComponentClass) throws IllegalStateException {
		isValidPrefix(vPComponentClass);
		isApiFound(vPComponentClass);
		checkViewImpl(vPComponentClass);
	}

	private void isValidPrefix(AnnotatedGenerateVPComponentClass vpComponentClass) {
		if (!vpComponentClass.getPresenterImplName().endsWith("Presenter")) {
			throw new IllegalStateException(
				String.format(
					"Имя %s должно оканчиваться на Presenter",
					vpComponentClass.getClassElement().getQualifiedName()
				)
			);
		}
	}

	private void isApiFound(AnnotatedGenerateVPComponentClass vPComponentClass) {
		final String possibleApi = vPComponentClass
			.getPresenterImplName()
			.substring(0, vPComponentClass.getPresenterImplName().length() - "Presenter".length());

		final TypeElement api = elementUtils.getTypeElement(vPComponentClass.getPackageName() + "." + possibleApi);

		if (api == null) {
			throw new IllegalStateException(
				String.format("Api %s не найдено", vPComponentClass.getPackageName() + "." + possibleApi)
			);
		}

		vPComponentClass.setApiClassElement(api);
	}

	private void checkViewImpl(AnnotatedGenerateVPComponentClass vPComponentClass) {
		final String possibleViewName = vPComponentClass
			.getApiClassElement()
			.getQualifiedName()
			.toString() + "View";

		final TypeElement possibleViewImpl = elementUtils.getTypeElement(possibleViewName);

		if (possibleViewImpl == null) {
			throw new IllegalStateException(
				String.format("View %s не найдено", vPComponentClass.getPackageName() + "." + possibleViewName)
			);
		}

		vPComponentClass.setViewImplClassElement(possibleViewImpl);
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


	private VariableElement getNestedSlot(TypeElement nestedPresenterElement) {
		VariableElement slot = null;

		for (Element element : elementUtils.getAllMembers(nestedPresenterElement)) {
			if (element.getKind() == ElementKind.FIELD) {
				final VariableElement variableElement = (VariableElement) element;

				if (typeUtils.isSameType(variableElement.asType(), elementUtils.getTypeElement(NestedSlot.class.getName()).asType())) {
					slot = variableElement;

					if (!isPublicStaticFinalField(slot)) {
						throw new IllegalStateException(
								String.format("%s должен быть public static final", variableElement)
						);
					}

					break;
				}

				break;
			}
		}

		return slot;
	}

	private VariableElement getNameToken(TypeElement nestedPresenterElement) {
		VariableElement nameToken = null;

		for (Element element : elementUtils.getAllMembers(nestedPresenterElement)) {
			if (element.getKind() == ElementKind.FIELD && element.getAnnotation(GenerateNameToken.class) != null) {
				nameToken = (VariableElement) element;

				if (!typeUtils.isSameType(nameToken.asType(), elementUtils.getTypeElement(String.class.getName()).asType())) {
					throw new IllegalStateException(
							String.format("%s тип должен быть String", nameToken)
					);
				}

				if (!isPublicStaticFinalField(nameToken)) {
					throw new IllegalStateException(
							String.format("%s должен быть public static final", nameToken)
					);
				}

				break;
			}
		}

		return nameToken;
	}

	private boolean isPublicStaticFinalField(VariableElement variableElement) {
		return variableElement.getModifiers().containsAll(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL));
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return new LinkedHashSet<String>() {{
			add(GenerateVPComponent.class.getCanonicalName());
		}};
	}
}
