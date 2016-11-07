package ru.vaadinp.compiler2;

import com.sun.source.util.Trees;
import freemarker.template.TemplateException;
import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.compiler2.datamodel.ClassDataModel;
import ru.vaadinp.compiler2.datamodel.MVPModel;
import ru.vaadinp.compiler2.datamodel.NestedMVPModel;
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
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aleksandr on 05.11.2016.
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

        if (nestedPresenterElements.size() > 0) {
            for (Element presenterElement : nestedPresenterElements) {
                final TypeElement presenterTypeElement = (TypeElement) presenterElement;

                if (isPresenterComponent(presenterTypeElement)) {
                    final MVPModel<?> mvp = new MVPBuilder<>(elementUtils, typeUtils, presenterTypeElement).build();
                    writeSource(mvp, "MVP.ftl");
                } else {
                    final NestedMVPModel nestedMVP = new NestedMVPBuilder(elementUtils, typeUtils, presenterTypeElement).build();
                    writeSource(nestedMVP, "NestedMVP.ftl");

                    if (nestedMVP.hasTokenSetModel()) {
                        writeSource(nestedMVP.getTokenSetModel(), "TokenSet.ftl");
                    }
                }
            }
        }

        return true;
    }

    private void writeSource(ClassDataModel cdm, String template) {
        try (Writer writer = filer.createSourceFile(cdm.getFqn()).openWriter()) {
            writer.write(JavaSourceGenerator.generateJavaSource(cdm, template));
        } catch (TemplateException | IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
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
        }};
    }
}
