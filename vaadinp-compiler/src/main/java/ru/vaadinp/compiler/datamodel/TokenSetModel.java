package ru.vaadinp.compiler.datamodel;

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

    private TokenModel defaultTokenModel;
    private TokenModel errorTokenModel;
    private TokenModel notFoundTokenModel;
    private ClassDataModel gatekeeper;

    public TokenSetModel(String apiName, String packageName) {
        super(apiName + "TokenSet", packageName);
    }

    public void addTokenModel(TokenModel tokenModel) {

        if (tokenModel.isDefault()) {
            defaultTokenModel = tokenModel;
        }
        if(tokenModel.isError()) {
            errorTokenModel = tokenModel;
        }
        if (tokenModel.isNotFound()) {
            notFoundTokenModel = tokenModel;
        }

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

    public TokenModel getDefaultTokenModel() {
        return defaultTokenModel;
    }

    public TokenModel getErrorTokenModel() {
        return errorTokenModel;
    }

    public TokenModel getNotFoundTokenModel() {
        return notFoundTokenModel;
    }

    public void setGatekeeper(ClassDataModel gatekeeper) {
        this.gatekeeper = gatekeeper;
    }

    public ClassDataModel getGatekeeper() {
        return gatekeeper;
    }

    public String getGatekeeperName() {
        if (gatekeeper == null) {
            return "";
        }
        return gatekeeper.getName();
    }
}
