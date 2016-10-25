package ru.vaadinp.compiler.processors;

import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.compiler.datamodel.*;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;

import javax.lang.model.element.*;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Arrays;

/**
 * Created by Aleksandr on 23.10.2016.
 */
public class NestedVPComponentScanner extends VPComponentScanner {
    private GenerateNestedVPComponentModel generateNestedVPComponentModel;

    public NestedVPComponentScanner(Elements elementUtils, Types typeUtils) {
        super(elementUtils, typeUtils);
    }


    @Override
    public GenerateVPComponentModel visitVariable(VariableElement ve, Void aVoid) {
        switch (ve.getKind()) {
            case FIELD:

                if (ve.asType().toString().equals(NestedSlot.class.getName())) {
                    if (!isPublicStaticFinalField(ve)) {

                    }

                    generateNestedVPComponentModel.getPresenterComponent().setNestedSlotMirror(new NestedSlotMirror(ve));
                }

                if (ve.asType().toString().equals(String.class.getName())) {
                    final GenerateNameToken generateNameTokenAnnotation = ve.getAnnotation(GenerateNameToken.class);

                    if (generateNameTokenAnnotation != null) {
                        generateNestedVPComponentModel.getPresenterComponent().setNameToken(ve.getConstantValue().toString());
                        generateNestedVPComponentModel.getPresenterComponent().setDefault(generateNameTokenAnnotation.isDefault());
                        generateNestedVPComponentModel.getPresenterComponent().setNotFound(generateNameTokenAnnotation.isNotFound());
                        generateNestedVPComponentModel.getPresenterComponent().setError(generateNameTokenAnnotation.isError());
                    }
                }

                break;

            case PARAMETER:

                if (ve.asType().toString().equals(NestedSlot.class.getName())) {
                    final RevealIn revealInAnnotation = ve.getAnnotation(RevealIn.class);

                    if (revealInAnnotation == null) {

                    }

                    try {
                        revealInAnnotation.value();
                    } catch (MirroredTypeException e) {
                        final TypeElement parentPresenter = (TypeElement) typeUtils.asElement(e.getTypeMirror());
                        generateNestedVPComponentModel.setParentPresenterElement(parentPresenter);

                        if (parentPresenter.asType().toString().equals(RootPresenter.class.getName())) {

                            final ApiMirror rootPresenterApi = getApi(parentPresenter);

                            generateNestedVPComponentModel.setParent(new GenerateNestedVPComponentModel(new AnnotatedNestedPresenter(parentPresenter, rootPresenterApi, getView(rootPresenterApi))));
                            generateNestedVPComponentModel.setRoot(true);
                        }
                    }
                }

                break;
        }
        return super.visitVariable(ve, aVoid);
    }

    @Override
    public GenerateNestedVPComponentModel scan(Element e, Void aVoid) {

        if (generateNestedVPComponentModel == null) {

            final ApiMirror api = getApi((TypeElement) e);

            generateNestedVPComponentModel = new GenerateNestedVPComponentModel(new AnnotatedNestedPresenter((TypeElement) e, api, getView(api)));
        }

        e.accept(this, aVoid);

        return generateNestedVPComponentModel;
    }

    private boolean isPublicStaticFinalField(VariableElement variableElement) {
        return variableElement.getModifiers().containsAll(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL));
    }
}
