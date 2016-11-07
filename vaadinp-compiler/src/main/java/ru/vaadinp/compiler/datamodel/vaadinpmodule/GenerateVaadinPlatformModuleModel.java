package ru.vaadinp.compiler.datamodel.vaadinpmodule;

import ru.vaadinp.compiler.datamodel.ClassDataModel;
import ru.vaadinp.compiler.datamodel.GenerateNestedMVPModel;
import ru.vaadinp.compiler.datamodel.GenerateMVPModel;
import ru.vaadinp.error.BaseErrorManager;
import ru.vaadinp.place.BasePlaceManager;
import ru.vaadinp.uri.PageUriFragmentSource;

import java.util.Collection;
import java.util.List;

/**
 * Created by Aleksandr on 22.10.2016.
 */
public class GenerateVaadinPlatformModuleModel extends ClassDataModel {
    private ClassDataModel placeManager;
    private ClassDataModel errorManager;
    private ClassDataModel uriFragmentSource;

    private List<GenerateMVPModel> vpComponents;
    private Collection<GenerateNestedMVPModel> vpNestedComponents;

    private GenerateNestedMVPModel defaultPlace;
    private GenerateNestedMVPModel errorPlace;
    private GenerateNestedMVPModel notFoundPlace;

    public GenerateVaadinPlatformModuleModel(String packageName,
                                             List<GenerateMVPModel> vpComponents,
                                             Collection<GenerateNestedMVPModel> vpNestedComponents) {

        super("VaadinPlatformModule", packageName);

        this.vpComponents = vpComponents;
        this.vpNestedComponents = vpNestedComponents;
    }

    public ClassDataModel getPlaceManager() {
        if (placeManager == null) {
            placeManager = new ClassDataModel(BasePlaceManager.class.getSimpleName(), BasePlaceManager.class.getPackage().getName());
        }
        return placeManager;
    }

    public void setPlaceManager(ClassDataModel placeManager) {
        this.placeManager = placeManager;
    }

    public ClassDataModel getErrorManager() {
        if (errorManager == null) {
            errorManager = new ClassDataModel(BaseErrorManager.class.getSimpleName(), BaseErrorManager.class.getPackage().getName());
        }
        return errorManager;
    }

    public void setErrorManager(ClassDataModel errorManager) {
        this.errorManager = errorManager;
    }

    public ClassDataModel getUriFragmentSource() {
        if (uriFragmentSource == null) {
            uriFragmentSource = new ClassDataModel(PageUriFragmentSource.class.getSimpleName(), PageUriFragmentSource.class.getPackage().getName());
        }
        return uriFragmentSource;
    }

    public void setUriFragmentSource(ClassDataModel uriFragmentSource) {
        this.uriFragmentSource = uriFragmentSource;
    }

    public List<GenerateMVPModel> getVpComponents() {
        return vpComponents;
    }

    public Collection<GenerateNestedMVPModel> getVpNestedComponents() {
        return vpNestedComponents;
    }

    public GenerateNestedMVPModel getDefaultPlace() {
        return defaultPlace;
    }

    public void setDefaultPlace(GenerateNestedMVPModel defaultPlace) {
        this.defaultPlace = defaultPlace;
    }

    public GenerateNestedMVPModel getErrorPlace() {
        return errorPlace;
    }

    public void setErrorPlace(GenerateNestedMVPModel errorPlace) {
        this.errorPlace = errorPlace;
    }

    public GenerateNestedMVPModel getNotFoundPlace() {
        return notFoundPlace;
    }

    public void setNotFoundPlace(GenerateNestedMVPModel notFoundPlace) {
        this.notFoundPlace = notFoundPlace;
    }
}
