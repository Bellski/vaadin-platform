package ru.vaadinp.compiler.processors;

import com.sun.tools.javac.code.Symbol;
import freemarker.template.TemplateException;
import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.compiler.JavaSourceGenerator;
import ru.vaadinp.compiler.datamodel.*;
import ru.vaadinp.compiler.datamodel.vaadinpmodule.GenerateVaadinPlatformModuleModel;
import ru.vaadinp.place.error.BaseErrorPlacePresenter;
import ru.vaadinp.place.notfound.BaseNotFoundPlacePresenter;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.PresenterComponent;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Created by Aleksandr on 23.10.2016.
 */
public class VaadinPlatformProcessor1 extends AbstractProcessor {

    private Messager messager;
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;

    private final Set<GenerateMVPModel> vpComponents = new LinkedHashSet<>();
    private final Map<TypeElement, GenerateNestedMVPModel> vpComponentByPresenterTypeElement = new HashMap<>();


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
        if (annotations.size() > 0) {

            GenerateNestedMVPModel rootComponent = null;

            GenerateNestedMVPModel defaultComponent = null;
            GenerateNestedMVPModel notFoundComponent = null;
            GenerateNestedMVPModel errorComponent = null;

            for (Element element : roundEnv.getElementsAnnotatedWith(GenerateMVP.class)) {

                if(isPresenterComponent((TypeElement) element)) {
                    GenerateMVPModel vpComponent = null;

                    try {
                        vpComponent = new VPComponentScanner(elementUtils, typeUtils).scan(element);
                        vpComponents.add(vpComponent);
                    } catch (IllegalStateException e) {
                        messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                    }

                    try (Writer writer = filer.createSourceFile(vpComponent.getFqn()).openWriter()) {
                        writer.write(JavaSourceGenerator.generateJavaSource(vpComponent, "SimpleVPComponent.ftl"));
                    } catch (TemplateException | IOException e) {
                        messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                    }

                } else if (isNestedPresenter((TypeElement) element)) {
                    GenerateNestedMVPModel nestedVPComponent = null;

                    try {
                        nestedVPComponent = (GenerateNestedMVPModel) new NestedVPComponentScanner(elementUtils, typeUtils).scan(element);
                        vpComponentByPresenterTypeElement.put(nestedVPComponent.getPresenterComponent().getPresenterElement(), nestedVPComponent);
                    } catch (IllegalStateException e) {
                        messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                    }

                    if (nestedVPComponent.isRoot()) {
                        rootComponent = nestedVPComponent;
                    }

                    if (nestedVPComponent.getPresenterComponent().getDefaultToken() != null) {
                        defaultComponent = nestedVPComponent;
                    }

                    if (nestedVPComponent.getPresenterComponent().getNotFoundToken() != null) {
                        notFoundComponent = nestedVPComponent;
                    }

                    if (nestedVPComponent.getPresenterComponent().getErrorToken() != null) {
                        errorComponent = nestedVPComponent;
                    }
                }

            }

            for (GenerateNestedMVPModel generateNestedMVPModel : vpComponentByPresenterTypeElement.values()) {
                if (generateNestedMVPModel.getParent() == null) {
                    generateNestedMVPModel.setParent(vpComponentByPresenterTypeElement.get(generateNestedMVPModel.getParentPresenterElement()));
                }

                try (Writer writer = filer.createSourceFile(generateNestedMVPModel.getFqn()).openWriter()) {
                    writer.write(JavaSourceGenerator.generateJavaSource(generateNestedMVPModel, "NestedVPComponent.ftl"));
                } catch (TemplateException | IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if (rootComponent != null) {
                final GenerateVaadinPlatformModuleModel generateVaadinPlatformModuleModel = new GenerateVaadinPlatformModuleModel(
                        rootComponent.getPackageName(),
                        vpComponents,
                        vpComponentByPresenterTypeElement.values()
                );


                if (notFoundComponent == null) {
                    final TypeElement notFoundPlaceTypeElement = elementUtils.getTypeElement(BaseNotFoundPlacePresenter.class.getName());

                    final ApiMirror api = getApi(notFoundPlaceTypeElement);

                    notFoundComponent = new GenerateNestedMVPModel(new AnnotatedNestedPresenter(notFoundPlaceTypeElement, api, getView(api)));
                }

                generateVaadinPlatformModuleModel.setNotFoundPlace(notFoundComponent);

                if (defaultComponent == null) {
                    defaultComponent = notFoundComponent;
                }

                generateVaadinPlatformModuleModel.setDefaultPlace(defaultComponent);

                if (errorComponent == null) {
                    final TypeElement notFoundPlaceTypeElement = elementUtils.getTypeElement(BaseErrorPlacePresenter.class.getName());

                    final ApiMirror api = getApi(notFoundPlaceTypeElement);

                    errorComponent = new GenerateNestedMVPModel(new AnnotatedNestedPresenter(notFoundPlaceTypeElement, api, getView(api)));
                }

                generateVaadinPlatformModuleModel.setErrorPlace(errorComponent);

                try (Writer writer = filer.createSourceFile(generateVaadinPlatformModuleModel.getFqn()).openWriter()) {
                    writer.write(JavaSourceGenerator.generateJavaSource(generateVaadinPlatformModuleModel, "VaadinPlatformModule.ftl"));
                } catch (TemplateException | IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }



        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {{
            add(GenerateMVP.class.getName());
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
                        BaseNestedPresenter
                                .class
                                .getName()
                ).asType()
        );
    }

    protected ApiMirror getApi(TypeElement presenterComponent) {
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

    protected ViewMirror getView(ApiMirror apiMirror) {
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
}
