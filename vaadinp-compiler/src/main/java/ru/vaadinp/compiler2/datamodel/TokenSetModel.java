package ru.vaadinp.compiler2.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandr on 06.11.2016.
 */
public class TokenSetModel extends ClassDataModel {
    private final List<TokenModel> tokenModels = new ArrayList<>();

    private List<String> encodedNameTokenConstantName = new ArrayList<>();
    private List<String> decodedNameTokenConstantName = new ArrayList<>();
    private List<String> tokenNameConstantName = new ArrayList<>();

    public TokenSetModel(String apiName, String packageName) {
        super(apiName + "TokenSet", packageName);
    }

    public void addTokenModel(TokenModel tokenModel) {
        this.tokenModels.add(tokenModel);
        this.encodedNameTokenConstantName.add(tokenModel.getEncodedNameTokenConstantName());
        this.decodedNameTokenConstantName.add(tokenModel.getDecodedNameTokenConstantName());
        this.tokenNameConstantName.add(tokenModel.getTokenNameConstantName());
    }

    public List<TokenModel> getTokenModels() {
        return tokenModels;
    }

    public List<String> getEncodedNameTokenConstantName() {
        return encodedNameTokenConstantName;
    }

    public List<String> getDecodedNameTokenConstantName() {
        return decodedNameTokenConstantName;
    }

    public List<String> getTokenNameConstantName() {
        return tokenNameConstantName;
    }
}
