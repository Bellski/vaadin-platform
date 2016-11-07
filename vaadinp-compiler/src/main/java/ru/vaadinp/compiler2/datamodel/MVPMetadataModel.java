package ru.vaadinp.compiler2.datamodel;

/**
 * Created by Aleksandr on 05.11.2016.
 */
public class MVPMetadataModel {
    private final String apiName;

    private final String viewApiName;
    private final String viewImplName;

    private final String presenterApiName;
    private final String presenterImplName;

    public MVPMetadataModel(String apiName) {
        this.apiName = apiName;

        viewApiName = apiName + "." + "View";
        viewImplName = apiName + "View";

        presenterApiName = apiName + "." + "Presenter";
        presenterImplName = apiName + "Presenter";
    }

    public String getApiName() {
        return apiName;
    }

    public String getViewApiName() {
        return viewApiName;
    }

    public String getViewImplName() {
        return viewImplName;
    }

    public String getPresenterApiName() {
        return presenterApiName;
    }

    public String getPresenterImplName() {
        return presenterImplName;
    }
}
