package ru.vaadinp.compiler2.datamodel;

import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import ru.vaadinp.annotations.GenerateMVPInfo;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.place.Place;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.MVPInfo;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.api.NestedMVP;
import ru.vaadinp.vp.api.PlaceMVP;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aleksandr on 05.11.2016.
 */
public class NestedMVPModel extends MVPModel<NestedMVPMetadataModel> {
    private ClassDataModel parent;

    private GenerateMVPInfo mvpInfoAnnotation;

    private TokenSetModel tokenSetModel;

    private String nestedSlotConstantName;

    public NestedMVPModel(NestedMVPMetadataModel nestedMVPMetadataModel, String packageName) {
        super(nestedMVPMetadataModel, packageName);
    }

    public void setParent(ClassDataModel parent) {
        this.parent = parent;
        importClassDataModel(parent);
    }

    public String getParentName() {
        return parent.getName();
    }

    @Override
    protected void imports() {
        importClass(NestedMVPImpl.class);
        importClass(NestedMVP.class);
        importClass(BaseNestedPresenter.class);
    }

    public void setMVPInfo(GenerateMVPInfo mvpInfoAnnotation) {
        this.mvpInfoAnnotation = mvpInfoAnnotation;
        importClass(MVPInfo.class);
    }

    public String getCaption() {
        return mvpInfoAnnotation == null? "" : mvpInfoAnnotation.caption();
    }

    public String getTitle() {
        return mvpInfoAnnotation == null? "" : mvpInfoAnnotation.title();
    }

    public String getHistoryToken() {
        return mvpInfoAnnotation == null? "" : mvpInfoAnnotation.historyToken();
    }

    public String getIcon() {
        return mvpInfoAnnotation == null? "" :  mvpInfoAnnotation.icon();
    }

    public int getPriority() {
        return mvpInfoAnnotation == null? -2 :  mvpInfoAnnotation.priority();
    }

    public boolean hasMVPInfo() {
        return mvpInfoAnnotation != null;
    }

    public void addTokenModel(TokenModel tokenModel) {
        if (tokenSetModel == null) {
            tokenSetModel = new TokenSetModel(getApiName(), getPackageName());
            importClass(Place.class);
            importClass(IntoMap.class);
            importClass(PlacesMap.class);
            importClass(StringKey.class);
            importClass(PlaceMVP.class);
        }
        tokenSetModel.addTokenModel(tokenModel);
    }

    public List<TokenModel> getTokenModels() {
        if (tokenSetModel == null) {
            return Collections.emptyList();
        }
        return tokenSetModel.getTokenModels();
    }

    public TokenSetModel getTokenSetModel() {
        return tokenSetModel;
    }

    public List<String> getEncodedNameTokenConstantName() {
        return tokenSetModel.getEncodedNameTokenConstantName();
    }

    public List<String> getDecodedNameTokenConstantName() {
        return tokenSetModel.getDecodedNameTokenConstantName();
    }

    public List<String> getTokenNameConstantName() {
        if (tokenSetModel == null) {
            return Collections.emptyList();
        }
        return tokenSetModel.getTokenNameConstantName();
    }

    public boolean hasTokenSetModel() {
        return tokenSetModel != null;
    }

    public String getNestedSlotConstantName() {
        return nestedSlotConstantName;
    }

    public void setNestedSlotConstantName(String nestedSlotConstantName) {
        this.nestedSlotConstantName = getPresenterImplName() + "." + nestedSlotConstantName;
        importClass(RevealIn.class);
        importClass(Provides.class);
        importClass(NestedSlot.class);
    }
}
