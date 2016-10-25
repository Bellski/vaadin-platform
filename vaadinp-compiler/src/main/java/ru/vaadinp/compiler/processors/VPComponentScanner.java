package ru.vaadinp.compiler.processors;

import com.sun.tools.javac.code.Symbol;
import ru.vaadinp.compiler.datamodel.AnnotatedPresenterComponent;
import ru.vaadinp.compiler.datamodel.ApiMirror;
import ru.vaadinp.compiler.datamodel.GenerateVPComponentModel;
import ru.vaadinp.compiler.datamodel.ViewMirror;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementScanner8;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by Aleksandr on 23.10.2016.
 */
public class VPComponentScanner extends ElementScanner8<GenerateVPComponentModel, Void> {;
    private GenerateVPComponentModel generateVPComponentModel;

    protected final Types typeUtils;
    protected Elements elementUtils;

    public VPComponentScanner(Elements elementUtils, Types typeUtils) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
    }


    @Override
    public GenerateVPComponentModel scan(Element e, Void aVoid) {
        if (generateVPComponentModel == null) {

            final ApiMirror api = getApi((TypeElement) e);

            generateVPComponentModel = new GenerateVPComponentModel(new AnnotatedPresenterComponent((TypeElement) e, api, getView(api)));
        }

        e.accept(this, aVoid);

        return generateVPComponentModel;
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
