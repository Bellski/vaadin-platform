package ru.vaadinp.compiler.processors;

import ru.vaadinp.annotations.GenerateMVPInfo;
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
    private GenerateNestedMVPModel generateNestedMVPModel;

    public NestedVPComponentScanner(Elements elementUtils, Types typeUtils) {
        super(elementUtils, typeUtils);
    }

    @Override
    public GenerateMVPModel visitType(TypeElement e, Void aVoid) {


        return super.visitType(e, aVoid);
    }

    @Override
    public GenerateMVPModel visitVariable(VariableElement ve, Void aVoid) {
        switch (ve.getKind()) {
            case FIELD:

                if (ve.asType().toString().equals(NestedSlot.class.getName())) {
                    if (!isPublicStaticFinalField(ve)) {

                    }

                    generateNestedMVPModel.getPresenterComponent().setNestedSlotMirror(new NestedSlotMirror(ve));
                }

                if (ve.asType().toString().equals(String.class.getName())) {
                    final GenerateNameToken generateNameTokenAnnotation = ve.getAnnotation(GenerateNameToken.class);

                    if (generateNameTokenAnnotation != null) {
//                        generateNestedMVPModel.getPresenterComponent().setNameToken(ve.getConstantValue().toString());
//                        generateNestedMVPModel.getPresenterComponent().setDefault(generateNameTokenAnnotation.isDefault());
//                        generateNestedMVPModel.getPresenterComponent().setNotFound(generateNameTokenAnnotation.isNotFound());
//                        generateNestedMVPModel.getPresenterComponent().setError(generateNameTokenAnnotation.isError());
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
                        generateNestedMVPModel.setParentPresenterElement(parentPresenter);

                        if (parentPresenter.asType().toString().equals(RootPresenter.class.getName())) {

                            final ApiMirror rootPresenterApi = getApi(parentPresenter);

                            generateNestedMVPModel.setParent(new GenerateNestedMVPModel(new AnnotatedNestedPresenter(parentPresenter, rootPresenterApi, getView(rootPresenterApi))));
                            generateNestedMVPModel.setRoot(true);
                        }
                    }
                }

                break;
        }
        return super.visitVariable(ve, aVoid);
    }

    @Override
    public GenerateNestedMVPModel scan(Element e, Void aVoid) {

        if (generateNestedMVPModel == null) {

            final ApiMirror api = getApi((TypeElement) e);

            generateNestedMVPModel = new GenerateNestedMVPModel(new AnnotatedNestedPresenter((TypeElement) e, api, getView(api)));
        }

        e.accept(this, aVoid);

        return generateNestedMVPModel;
    }

    private boolean isPublicStaticFinalField(VariableElement variableElement) {
        return variableElement.getModifiers().containsAll(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL));
    }
}
