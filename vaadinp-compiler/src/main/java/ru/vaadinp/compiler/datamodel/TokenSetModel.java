package ru.vaadinp.compiler.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandr on 05.11.2016.
 */
public class TokenSetModel extends ClassDataModel {
    private final List<TokenModel> tokenModelList = new ArrayList<>();
    private final List<String> tokenConstantNameList = new ArrayList<>();

    public TokenSetModel(String name, String packageName) {
        super(name, packageName);
    }

    public void add(TokenModel tokenModel) {
        tokenModelList.add(tokenModel);
        tokenConstantNameList.add(tokenModel.getTokenNameConstantName());
    }

    public List<TokenModel> getTokenModelList() {
        return tokenModelList;
    }

    public List<String> getTokenConstantNameList() {
        return tokenConstantNameList;
    }
}
