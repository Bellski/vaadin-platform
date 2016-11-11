package ru.vaadinp.compiler;

import ru.vaadinp.annotations.GenerateMVPInfo;
import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.compiler.datamodel.*;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Aleksandr on 05.11.2016.
 */
public class NestedMVPBuilder extends MVPBuilder<NestedMVPModel> {
    public NestedMVPBuilder(Elements elementUtils, Types typeUtils, TypeElement presenterElement) {
        super(elementUtils, typeUtils, presenterElement);
    }

    @Override
    protected void processGenerateNameTokens(GenerateNameToken[] generateNameTokens) {
        for (GenerateNameToken generateNameToken : generateNameTokens) {
            final TokenModel tokenModel = new TokenModel(mvpModel.getApiName() + "TokenSet");

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

            tokenModel.setEncodedNameToken(encodedNameTokenJoiner.toString());
            tokenModel.setDecodedNameToken(generateNameToken.nameToken());
            tokenModel.setParameterNames(paramNames);
            tokenModel.setParameterIndexes(paramIndexes);
            tokenModel.setDefault(generateNameToken.isDefault());
            tokenModel.setError(generateNameToken.isError());
            tokenModel.setNotFound(generateNameToken.isNotFound());

            mvpModel.addTokenModel(tokenModel);
        }
    }

    @Override
    protected void processGenerateMVPInfo(GenerateMVPInfo generateMVPInfo) {
        if (generateMVPInfo.priority() != -2) {
            mvpModel.setMVPInfo(generateMVPInfo);
        }
    }

    @Override
    protected void processRevealIn(RevealIn revealIn) {
        try {
            revealIn.value();
        } catch (MirroredTypeException e) {
            final Element parentElement = typeUtils.asElement(e.getTypeMirror());
            mvpModel.setParent(new ClassDataModel(getElementName(parentElement).replace("Presenter", "MVP"), getElementPackageName(parentElement)));
        }
    }

    @Override
    protected void processNestedSlot(VariableElement nestedSlotElement) {
        mvpModel.setNestedSlotConstantName(nestedSlotElement.getSimpleName().toString());
    }

    @Override
    protected MVPModel<?> createMVPModel(MVPMetadataModel mvpMetadataModel, String packageName) {
        return new NestedMVPModel((NestedMVPMetadataModel) mvpMetadataModel, packageName);
    }

    @Override
    protected MVPMetadataModel createMVPMetadataModel(String apiName) {
        return new NestedMVPMetadataModel(apiName);
    }

    private String getElementName(Element element) {
        return element.getSimpleName().toString();
    }

    private String getElementPackageName(Element element) {
        return elementUtils.getPackageOf(element).getQualifiedName().toString();
    }
}
