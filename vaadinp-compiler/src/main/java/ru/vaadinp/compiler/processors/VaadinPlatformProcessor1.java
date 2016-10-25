package ru.vaadinp.compiler.processors;

import com.sun.tools.javac.code.Symbol;
import freemarker.template.TemplateException;
import ru.vaadinp.annotations.GenerateVPComponent;
import ru.vaadinp.compiler.JavaSourceGenerator;
import ru.vaadinp.compiler.datamodel.*;
import ru.vaadinp.compiler.datamodel.vaadinpmodule.GenerateVaadinPlatformModuleModel;
import ru.vaadinp.place.error.BaseErrorPlacePresenter;
import ru.vaadinp.place.notfound.BaseNotFoundPlacePresenter;
import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.PresenterComponent;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
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

    private final Set<GenerateVPComponentModel> vpComponents = new LinkedHashSet<>();
    private final Map<TypeElement, GenerateNestedVPComponentModel> vpComponentByPresenterTypeElement = new HashMap<>();


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

            GenerateNestedVPComponentModel rootComponent = null;

            GenerateNestedVPComponentModel defaultComponent = null;
            GenerateNestedVPComponentModel notFoundComponent = null;
            GenerateNestedVPComponentModel errorComponent = null;

            for (Element element : roundEnv.getElementsAnnotatedWith(GenerateVPComponent.class)) {

                if(isPresenterComponent((TypeElement) element)) {
                    final GenerateVPComponentModel vpComponent = new VPComponentScanner(elementUtils, typeUtils).scan(element);
                    vpComponents.add(vpComponent);

                    try (Writer writer = filer.createSourceFile(vpComponent.getFqn()).openWriter()) {
                        writer.write(JavaSourceGenerator.generateJavaSource(vpComponent, "SimpleVPComponent.ftl"));
                    } catch (TemplateException | IOException e) {
                        e.printStackTrace();
                    }

                } else if (isNestedPresenter((TypeElement) element)) {
                    final GenerateNestedVPComponentModel nestedVPComponent = (GenerateNestedVPComponentModel) new NestedVPComponentScanner(elementUtils, typeUtils).scan(element);
                    vpComponentByPresenterTypeElement.put(nestedVPComponent.getPresenterComponent().getPresenterElement(), nestedVPComponent);

                    if (nestedVPComponent.isRoot()) {
                        rootComponent = nestedVPComponent;
                    }

                    if (nestedVPComponent.getPresenterComponent().isDefault()) {
                        defaultComponent = nestedVPComponent;
                    }

                    if (nestedVPComponent.getPresenterComponent().isNotFound()) {
                        notFoundComponent = nestedVPComponent;
                    }

                    if (nestedVPComponent.getPresenterComponent().isError()) {
                        errorComponent = nestedVPComponent;
                    }
                }

            }

            for (GenerateNestedVPComponentModel generateNestedVPComponentModel : vpComponentByPresenterTypeElement.values()) {
                if (generateNestedVPComponentModel.getParent() == null) {
                    generateNestedVPComponentModel.setParent(vpComponentByPresenterTypeElement.get(generateNestedVPComponentModel.getParentPresenterElement()));
                }

                try (Writer writer = filer.createSourceFile(generateNestedVPComponentModel.getFqn()).openWriter()) {

                    if (generateNestedVPComponentModel.getPresenterComponent().getNameToken() == null) {
                        writer.write(JavaSourceGenerator.generateJavaSource(generateNestedVPComponentModel, "NestedVPComponent.ftl"));
                    } else {
                        writer.write(JavaSourceGenerator.generateJavaSource(generateNestedVPComponentModel, "PlaceVPComponent.ftl"));
                    }

                } catch (TemplateException | IOException e) {
                    e.printStackTrace();
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

                    notFoundComponent = new GenerateNestedVPComponentModel(new AnnotatedNestedPresenter(notFoundPlaceTypeElement, api, getView(api)));
                }

                generateVaadinPlatformModuleModel.setNotFoundPlace(notFoundComponent);

                if (defaultComponent == null) {
                    defaultComponent = notFoundComponent;
                }

                generateVaadinPlatformModuleModel.setDefaultPlace(defaultComponent);

                if (errorComponent == null) {
                    final TypeElement notFoundPlaceTypeElement = elementUtils.getTypeElement(BaseErrorPlacePresenter.class.getName());

                    final ApiMirror api = getApi(notFoundPlaceTypeElement);

                    errorComponent = new GenerateNestedVPComponentModel(new AnnotatedNestedPresenter(notFoundPlaceTypeElement, api, getView(api)));
                }

                generateVaadinPlatformModuleModel.setErrorPlace(errorComponent);

                try (Writer writer = filer.createSourceFile(generateVaadinPlatformModuleModel.getFqn()).openWriter()) {
                    writer.write(JavaSourceGenerator.generateJavaSource(generateVaadinPlatformModuleModel, "VaadinPlatformModule.ftl"));
                } catch (TemplateException | IOException e) {
                    e.printStackTrace();
                }
            }


        }



        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {{
            add(GenerateVPComponent.class.getName());
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
