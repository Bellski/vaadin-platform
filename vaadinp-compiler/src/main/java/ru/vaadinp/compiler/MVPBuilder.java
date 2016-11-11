package ru.vaadinp.compiler;

import com.sun.tools.javac.code.Symbol;
import ru.vaadinp.compiler.datamodel.MVPMetadataModel;
import ru.vaadinp.compiler.datamodel.MVPModel;
import ru.vaadinp.compiler.datamodel.PresenterScanner;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by Aleksandr on 05.11.2016.
 */
public class MVPBuilder<MODEL extends MVPModel<?>> extends PresenterScanner {
    protected MODEL mvpModel;

    public MVPBuilder(Elements elementUtils, Types typeUtils, TypeElement presenterElement) {
        super(elementUtils, typeUtils, presenterElement);
    }

    public final MODEL build() {
        mvpModel = (MODEL) createMVPModel(
                createMVPMetadataModel(
                        cutApiName(presenterElement)),
                Symbol.class.cast(presenterElement).packge().getQualifiedName().toString()
        );
        scan();
        return mvpModel;
    }

    protected MVPModel<?> createMVPModel(MVPMetadataModel mvpMetadataModel, String packageName) {
        return new MVPModel<>(mvpMetadataModel, packageName);
    }

    protected MVPMetadataModel createMVPMetadataModel(String apiName) {
        return new MVPMetadataModel(apiName);
    }

    private String cutApiName(TypeElement presenterTypeElement) {
        final String elementName = presenterTypeElement.getSimpleName().toString();
        return elementName.substring(0, elementName.length() - "Presenter".length());
    }
}
