package ru.vaadinp.compiler;

import com.sun.tools.javac.code.Symbol;
import freemarker.template.TemplateException;
import ru.vaadinp.annotations.*;
import ru.vaadinp.compiler.datamodel.*;
import ru.vaadinp.compiler.datamodel.base.BaseErrorPlaceMVPModel;
import ru.vaadinp.compiler.datamodel.base.BaseNotFoundMVPModel;
import ru.vaadinp.error.BaseErrorManager;
import ru.vaadinp.place.BasePlaceManager;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.uri.BaseUriFragmentSource;
import ru.vaadinp.uri.PageUriFragmentSource;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.PresenterComponent;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Aleksandr on 05.11.2016.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MVPProcessor extends AbstractProcessor {

	private final boolean testWorkAround;

	private Messager messager;
	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;

	public MVPProcessor(boolean testWorkAround) {
		this.testWorkAround = testWorkAround;
	}

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
		final Set<? extends Element> nestedPresenterElements = roundEnv.getElementsAnnotatedWith(GenerateMVP.class);

		if (nestedPresenterElements.size() > 0) {
			final Set<? extends Element> globalGateKeeperElements = roundEnv.getElementsAnnotatedWith(GlobalGateKeeper.class);

			ClassDataModel globalGatekeeperModel = null;

			if (!globalGateKeeperElements.isEmpty()) {
				final Element gk = globalGateKeeperElements.iterator().next();
				globalGatekeeperModel = new ClassDataModel(
					elementUtils.getTypeElement(
						Symbol.class.cast(gk).getQualifiedName().toString().replaceAll("Presenter", "MVP")
					)
				);
			}

			final List<MVPModel<?>> mvpModels = new ArrayList<>();

			NestedMVPModel rootMVP = null;

			NestedMVPModel errorMVPModel = null;
			NestedMVPModel notFoundMVPModel = null;
			NestedMVPModel defaultMVPModel = null;

			for (Element presenterElement : nestedPresenterElements) {
				final TypeElement presenterTypeElement = (TypeElement) presenterElement;

				if (isPresenterComponent(presenterTypeElement)) {
					final MVPModel<?> mvp = new MVPBuilder<>(elementUtils, typeUtils, presenterTypeElement).build();
					writeSource(mvp, "MVP.ftl");

					mvpModels.add(mvp);
				} else {
					final NestedMVPModel nestedMVP = new NestedMVPBuilder(elementUtils, typeUtils, presenterTypeElement).build();

					if (RootMVP.class.getName().equals(nestedMVP.getParent().getFqn())) {
						rootMVP = nestedMVP;
					}

					writeSource(nestedMVP, "NestedMVP.ftl");

					if (nestedMVP.hasTokenSetModel()) {
						final TokenSetModel tokenSetModel = nestedMVP.getTokenSetModel();
						tokenSetModel.setGatekeeper(globalGatekeeperModel);

						if (tokenSetModel.getErrorTokenModel() != null) {
							errorMVPModel = nestedMVP;
						}

						if (tokenSetModel.getNotFoundTokenModel() != null) {
							notFoundMVPModel = nestedMVP;
						}

						if (tokenSetModel.getDefaultTokenModel() != null) {
							defaultMVPModel = nestedMVP;
						}

						writeSource(nestedMVP.getTokenSetModel(), "TokenSet.ftl");
					}

					mvpModels.add(nestedMVP);
				}
			}

			if (errorMVPModel == null) {
				errorMVPModel = new BaseErrorPlaceMVPModel();
				mvpModels.add(errorMVPModel);
			}

			if (notFoundMVPModel == null) {
				notFoundMVPModel = new BaseNotFoundMVPModel();
				mvpModels.add(notFoundMVPModel);
			}

			if (defaultMVPModel == null) {
				defaultMVPModel = notFoundMVPModel;
			}

			if (rootMVP != null) {
				final ApplicationModuleModel applicationModuleModel = new ApplicationModuleModel(rootMVP.getApiName(), rootMVP.getPackageName());
				applicationModuleModel.setMvpModels(mvpModels);
				applicationModuleModel.setErrorMVPModel(errorMVPModel);
				applicationModuleModel.setNotFoundMVPModel(notFoundMVPModel);
				applicationModuleModel.setDefaultMVPModel(defaultMVPModel);

				final Set<? extends Element> overridePlaceManagerAnnotations = roundEnv.getElementsAnnotatedWith(OverridePlaceManager.class);

				if (!overridePlaceManagerAnnotations.isEmpty()) {
					applicationModuleModel.setPlaceManagerModel(new ClassDataModel(overridePlaceManagerAnnotations.iterator().next()));
				} else {
					applicationModuleModel.setPlaceManagerModel(new ClassDataModel(elementUtils.getTypeElement(BasePlaceManager.class.getName())));
				}

				final Set<? extends Element> overrideErrorManagerAnnotations = roundEnv.getElementsAnnotatedWith(OverrideErrorManager.class);

				if (!overrideErrorManagerAnnotations.isEmpty()) {
					applicationModuleModel.setErrorManagerModel(new ClassDataModel(overrideErrorManagerAnnotations.iterator().next()));
				} else {
					applicationModuleModel.setErrorManagerModel(new ClassDataModel(elementUtils.getTypeElement(BaseErrorManager.class.getName())));
				}

				final Set<? extends Element> overrideUriFragmentSourceAnnotations = roundEnv.getElementsAnnotatedWith(OverrideUriFragmentSource.class);

				if (!overrideUriFragmentSourceAnnotations.isEmpty()) {
					applicationModuleModel.setUriFragmentSourceModel(new ClassDataModel(overrideUriFragmentSourceAnnotations.iterator().next()));
				} else {
					if (testWorkAround) {
						applicationModuleModel.setUriFragmentSourceModel(new ClassDataModel(BaseUriFragmentSource.class.getSimpleName(), BaseUriFragmentSource.class.getPackage().getName()));
					} else {
						applicationModuleModel.setUriFragmentSourceModel(new ClassDataModel(PageUriFragmentSource.class.getSimpleName(), PageUriFragmentSource.class.getPackage().getName()));
					}
				}

				writeSource(applicationModuleModel, "ApplicationModule.ftl");
			}
		}

		return true;
	}

	private void writeSource(ClassDataModel cdm, String template) {
		try (Writer writer = filer.createSourceFile(cdm.getFqn()).openWriter()) {
			writer.write(JavaSourceGenerator.generateJavaSource(cdm, template));
		} catch (TemplateException | IOException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			messager.printMessage(Diagnostic.Kind.ERROR, sw.toString());
		}
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
				BaseNestedPresenter
					.class
					.getName()
			).asType()
		);
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return new HashSet<String>() {{
			add(GenerateMVP.class.getName());
			add(OverridePlaceManager.class.getName());
			add(OverrideErrorManager.class.getName());
			add(OverrideUriFragmentSource.class.getName());
		}};
	}
}
