package ru.vaadinp.compiler.datamodel;

import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.GenerateMVPInfo;
import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by Aleksandr on 05.11.2016.
 */
public class PresenterScanner {
    protected final Elements elementUtils;
    protected final Types typeUtils;
    protected final TypeElement presenterElement;

    private class InnerScanner extends ElementScanner8<Void, Void> {
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {

            switch (e.getKind()) {
                case FIELD:
                    if (e.asType().toString().equals(NestedSlot.class.getName()))  {
                        processNestedSlot(e);
                    }
                    break;
                case PARAMETER:
                    final RevealIn revealInAnnotation = e.getAnnotation(RevealIn.class);

                    if (revealInAnnotation != null) {
                        processRevealIn(revealInAnnotation);
                    }

                    break;
            }

            return super.visitVariable(e, aVoid);
        }
    }

    public PresenterScanner(Elements elementUtils, Types typeUtils, TypeElement presenterElement) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        this.presenterElement = presenterElement;
    }

    protected void scan() {
        final GenerateMVP generateAnnotationMVP = presenterElement.getAnnotation(GenerateMVP.class);

        if (generateAnnotationMVP.nameTokens().length > 0) {
            processGenerateNameTokens(generateAnnotationMVP.nameTokens());
        }

        if (generateAnnotationMVP.mvpInfo().priority() != -2) {
            processGenerateMVPInfo(generateAnnotationMVP.mvpInfo());
        }

        new InnerScanner().scan(presenterElement);
    }

    protected void processGenerateNameTokens(GenerateNameToken[] generateNameTokens) {

    }

    protected void processGenerateMVPInfo(GenerateMVPInfo generateMVPInfo) {

    }

    protected void processRevealIn(RevealIn revealIn) {

    }

    protected void processNestedSlot(VariableElement nestedSlotElement) {

    }
}
