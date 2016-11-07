package ru.vaadinp.compiler.processors;

import com.sun.tools.javac.code.Symbol;
import freemarker.template.TemplateException;
import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.compiler2.JavaSourceGenerator;
import ru.vaadinp.compiler.datamodel.*;
import ru.vaadinp.compiler.datamodel.vaadinpmodule.GenerateVaadinPlatformModuleModel;
import ru.vaadinp.place.error.BaseErrorPlacePresenter;
import ru.vaadinp.place.error.BaseErrorPlaceToken;
import ru.vaadinp.place.notfound.BaseNotFoundPlacePresenter;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceToken;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.PresenterComponent;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Created by Aleksandr on 03.11.2016.
 */
public class MVPProcessor extends AbstractProcessor {
    private Messager messager;
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;

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

        if (!nestedPresenterElements.isEmpty()) {
            GenerateNestedMVPModel rootComponent = null;

            GenerateNestedMVPModel defaultComponent = null;
            GenerateNestedMVPModel notFoundComponent = null;
            GenerateNestedMVPModel errorComponent = null;


            final List<GenerateMVPModel> mvpModels = new ArrayList<>();
            final List<GenerateNestedMVPModel> nestedMVPs = new ArrayList<>();

            for (Element element : nestedPresenterElements) {
                final TypeElement nestedPresenterElement = (TypeElement) element;

                final ApiMirror mvpApi = findApi(nestedPresenterElement);
                final ViewMirror mvpView = findView(mvpApi);

                if (isPresenterComponent(nestedPresenterElement)) {
                    final GenerateMVPModel generateMVPModel = new GenerateMVPModel(new AnnotatedPresenterComponent(nestedPresenterElement, mvpApi, mvpView));
                    mvpModels.add(generateMVPModel);

                } else if(isNestedPresenter(nestedPresenterElement)) {
                    final GenerateMVP generateMVPAnnotation = nestedPresenterElement.getAnnotation(GenerateMVP.class);

                    final AnnotatedNestedPresenter nestedPresenter = new AnnotatedNestedPresenter(nestedPresenterElement, mvpApi, mvpView);
                    nestedPresenter.setNestedSlotMirror(findNestedSlot(nestedPresenterElement));

                    final GenerateNestedMVPModel generateMVPModel = new GenerateNestedMVPModel(nestedPresenter);

                    final  GenerateNestedMVPModel parentElement = findParent(nestedPresenterElement);
                    generateMVPModel.setParent(parentElement);

                    if (generateMVPAnnotation.mvpInfo().priority() != -2) {
                        generateMVPModel.setMvpInfo(generateMVPAnnotation.mvpInfo());
                    }

                    if (parentElement.getPresenterComponent().getPresenterElement().asType().toString().equals(RootPresenter.class.getName())) {
                        generateMVPModel.setRoot(true);
                        rootComponent = generateMVPModel;
                    }

                    for (GenerateNameToken generateNameToken : generateMVPAnnotation.nameTokens()) {
                        final TokenModel tokenModel = new TokenModel();

                        final String nameToken = generateNameToken.nameToken();
                        final String[] nameTokenParts = nameToken.split("/");

                        final StringJoiner encodedNameTokenJoiner = new StringJoiner("/");

                        final List<String> paramNames = new ArrayList<>();
                        final List<Integer> paramIndexes = new ArrayList<>();

                        for (int i = 0; i < nameTokenParts.length; i++) {
                            final String nameTokenPart = nameTokenParts[i];

                            if (nameTokenPart.charAt(0) == '{') {
                                paramNames.add(nameTokenPart.substring(1, nameTokenPart.length() -1));
                                paramIndexes.add(i);

                                encodedNameTokenJoiner.add("?");
                            } else {
                                encodedNameTokenJoiner.add(nameTokenPart);
                            }
                        }

                        tokenModel.setDecodedNameToken(generateNameToken.nameToken());
                        tokenModel.setParameterNames(paramNames);
                        tokenModel.setParameterIndexes(paramIndexes);

                        if (generateNameToken.isDefault()) {
                            nestedPresenter.setDefaultToken(tokenModel);
                            defaultComponent = generateMVPModel;
                        }

                        if (generateNameToken.isError()) {
                            nestedPresenter.setErrorToken(tokenModel);
                            errorComponent = generateMVPModel;
                        }

                        if (generateNameToken.isNotFound()) {
                            nestedPresenter.setNotFoundToken(tokenModel);
                            notFoundComponent = generateMVPModel;
                        }

                        nestedPresenter.addTokenModel(tokenModel);

                    }



                    nestedMVPs.add(generateMVPModel);
                }
            }


            if (rootComponent != null) {
                final GenerateVaadinPlatformModuleModel generateVaadinPlatformModuleModel = new GenerateVaadinPlatformModuleModel(
                        rootComponent.getPackageName(),
                        mvpModels,
                        nestedMVPs
                );


                if (notFoundComponent == null) {
                    final TypeElement notFoundPlaceTypeElement = elementUtils.getTypeElement(BaseNotFoundPlacePresenter.class.getName());

                    final ApiMirror api = findApi(notFoundPlaceTypeElement);

                    final AnnotatedNestedPresenter notFoundPresenter = new AnnotatedNestedPresenter(notFoundPlaceTypeElement, api, findView(api));
                    final TokenModel nameTokenModel = new TokenModel();
                    nameTokenModel.setEncodedNameToken(BaseNotFoundPlaceToken.DECODED_VAADINP_NOTFOUND);
                    nameTokenModel.setDecodedNameToken(BaseNotFoundPlaceToken.DECODED_VAADINP_NOTFOUND);

                    notFoundPresenter.addTokenModel(nameTokenModel);
                    notFoundPresenter.setNotFoundToken(nameTokenModel);

                    notFoundComponent = new GenerateNestedMVPModel(notFoundPresenter);

                    if (defaultComponent == null) {
                        notFoundPresenter.setDefaultToken(nameTokenModel);
                    }
                }

                generateVaadinPlatformModuleModel.setNotFoundPlace(notFoundComponent);

                if (defaultComponent == null) {
                    defaultComponent = notFoundComponent;
                }

                generateVaadinPlatformModuleModel.setDefaultPlace(defaultComponent);

                if (errorComponent == null) {
                    final TypeElement notFoundPlaceTypeElement = elementUtils.getTypeElement(BaseErrorPlacePresenter.class.getName());

                    final ApiMirror api = findApi(notFoundPlaceTypeElement);

                    final AnnotatedNestedPresenter errorPresenter = new AnnotatedNestedPresenter(notFoundPlaceTypeElement, api, findView(api));
                    final TokenModel tokenModel = new TokenModel();
                    tokenModel.setEncodedNameToken(BaseErrorPlaceToken.ENCODED_VAADINP_ERROR);
                    tokenModel.setDecodedNameToken(BaseErrorPlaceToken.DECODED_VAADINP_ERROR);

                    errorPresenter.addTokenModel(tokenModel);
                    errorPresenter.setErrorToken(tokenModel);

                    errorComponent = new GenerateNestedMVPModel(errorPresenter);
                }

                generateVaadinPlatformModuleModel.setErrorPlace(errorComponent);

                try (Writer writer = filer.createSourceFile(generateVaadinPlatformModuleModel.getFqn()).openWriter()) {
                    final String vmvpModule = JavaSourceGenerator.generateJavaSource(generateVaadinPlatformModuleModel, "VaadinPlatformModule.ftl");
                    writer.write(new Formatter().format(vmvpModule).toString());
                } catch (TemplateException | IOException e) {
                    throw new RuntimeException(e);
                }
            }

            for (GenerateMVPModel mvpModel : mvpModels) {
                try (Writer writer = filer.createSourceFile(mvpModel.getFqn()).openWriter()) {
                    String mvp = JavaSourceGenerator.generateJavaSource(mvpModel, "SimpleVPComponent.ftl");
                    writer.write(new Formatter().format(mvp).toString());
                } catch (TemplateException | IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                }
            }

            for (GenerateNestedMVPModel generateNestedMVPModel : nestedMVPs) {
                try (Writer writer = filer.createSourceFile(generateNestedMVPModel.getFqn()).openWriter()) {
                    final String nestedMVP = JavaSourceGenerator.generateJavaSource(generateNestedMVPModel, "NestedVPComponent.ftl");
                    writer.write(new Formatter().format(nestedMVP).toString());
                } catch (TemplateException | IOException e) {
                    throw new RuntimeException(e);
                }

                try (Writer writer = filer.createSourceFile(generateNestedMVPModel.getPresenterComponent().getTokenSetModel().getFqn()).openWriter()) {
                    final String vmvpModule = JavaSourceGenerator.generateJavaSource(generateNestedMVPModel.getPresenterComponent(), "Token.ftl");
                    writer.write(new Formatter().format(vmvpModule).toString());
                } catch (TemplateException | IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        return true;
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

    protected ApiMirror findApi(TypeElement presenterComponent) {
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

    protected ViewMirror findView(ApiMirror apiMirror) {
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

    private GenerateNestedMVPModel findParent(TypeElement typeElement) {
        for (Element element : elementUtils.getAllMembers(typeElement)) {
            if (element.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructor = (ExecutableElement) element;
                for (VariableElement variableElement : constructor.getParameters()) {
                    final RevealIn revealInAnnotation = variableElement.getAnnotation(RevealIn.class);

                    if (revealInAnnotation != null) {
                        try {
                            revealInAnnotation.value();
                        } catch (MirroredTypeException e) {
                            final TypeElement parentPresenter = (TypeElement) typeUtils.asElement(e.getTypeMirror());

                            final ApiMirror parentApi = findApi(parentPresenter);

                            return new GenerateNestedMVPModel(new AnnotatedNestedPresenter(parentPresenter, parentApi, findView(parentApi)));
                        }

                        break;
                    }
                }
            }
        }
        return null;
    }

    private NestedSlotMirror findNestedSlot(TypeElement nestedPresenterElement) {
        for (Element element : elementUtils.getAllMembers(nestedPresenterElement)) {
            if (element.getKind() == ElementKind.FIELD) {
                if (element.asType().toString().equals(NestedSlot.class.getName())) {
                    return new NestedSlotMirror((VariableElement) element);
                }
            }
        }

        return null;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {{
            add(GenerateMVP.class.getName());
        }};
    }
}
