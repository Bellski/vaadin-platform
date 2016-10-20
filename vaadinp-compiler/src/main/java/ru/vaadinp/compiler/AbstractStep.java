package ru.vaadinp.compiler;

import freemarker.template.TemplateException;
import ru.vaadinp.annotations.GenerateVPComponent;
import ru.vaadinp.compiler.model.JavaClassModel;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;

/**
 * Created by Aleksandr on 17.10.2016.
 */
public abstract class AbstractStep {
    protected final Types typeUtils;
    protected final Elements elementUtils;
    protected final Filer filer;

    public AbstractStep(Types typeUtils, Elements elementUtils, Filer filer) {
        this.typeUtils = typeUtils;
        this.elementUtils = elementUtils;
        this.filer = filer;
    }

    protected String nameToString(Name name) {
        return name.toString();
    }

    protected JavaClassModel elementToClassModel(Element element) {
        return new JavaClassModel(
                nameToString(elementUtils.getPackageOf(element).getQualifiedName()),
                nameToString(element.getSimpleName())
        );
    }

    protected JavaClassModel viewImplValue(GenerateVPComponent vpComponentAnnotation) {
        JavaClassModel javaClassModel = null;

//        try {
//            vpComponentAnnotation.viewImpl();
//        } catch (MirroredTypeException e) {
//            javaClassModel = elementToClassModel(typeUtils.asElement(e.getTypeMirror()));
//        }
//
//        if (javaClassModel == null) {
//            throw new NullPointerException();
//        }

        return javaClassModel;
    }

    protected String apiClassName(GenerateVPComponent vpComponentAnnotation, TypeElement presenter) {
        String apiClassName = null;

//        try {
//            vpComponentAnnotation.api();
//        } catch (MirroredTypeException e) {
//            apiClassName = typeUtils.asElement(e.getTypeMirror()).getSimpleName().toString();
//        }
//
//        if (apiClassName == null) {
//            throw new NullPointerException();
//        }

        return apiClassName;
    }

    public abstract void process(TypeElement presenter) throws IOException, TemplateException;
}
