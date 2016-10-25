package ru.vaadinp.compiler.datamodel.vaadinpmodule;

import ru.vaadinp.compiler.datamodel.ClassDataModel;
import ru.vaadinp.compiler.datamodel.GenerateNestedVPComponentModel;
import ru.vaadinp.compiler.datamodel.GenerateVPComponentModel;
import ru.vaadinp.error.BaseErrorManager;
import ru.vaadinp.place.BasePlaceManager;
import ru.vaadinp.uri.PageUriFragmentSource;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Aleksandr on 22.10.2016.
 */
public class GenerateVaadinPlatformModuleModel extends ClassDataModel {
    private ClassDataModel placeManager;
    private ClassDataModel errorManager;
    private ClassDataModel uriFragmentSource;

    private Set<GenerateVPComponentModel> vpComponents;
    private Collection<GenerateNestedVPComponentModel> vpNestedComponents;

    private GenerateNestedVPComponentModel defaultPlace;
    private GenerateNestedVPComponentModel errorPlace;
    private GenerateNestedVPComponentModel notFoundPlace;

    public GenerateVaadinPlatformModuleModel(String packageName,
                                             Set<GenerateVPComponentModel> vpComponents,
                                             Collection<GenerateNestedVPComponentModel> vpNestedComponents) {

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

    public Set<GenerateVPComponentModel> getVpComponents() {
        return vpComponents;
    }

    public Collection<GenerateNestedVPComponentModel> getVpNestedComponents() {
        return vpNestedComponents;
    }

    public GenerateNestedVPComponentModel getDefaultPlace() {
        return defaultPlace;
    }

    public void setDefaultPlace(GenerateNestedVPComponentModel defaultPlace) {
        this.defaultPlace = defaultPlace;
    }

    public GenerateNestedVPComponentModel getErrorPlace() {
        return errorPlace;
    }

    public void setErrorPlace(GenerateNestedVPComponentModel errorPlace) {
        this.errorPlace = errorPlace;
    }

    public GenerateNestedVPComponentModel getNotFoundPlace() {
        return notFoundPlace;
    }

    public void setNotFoundPlace(GenerateNestedVPComponentModel notFoundPlace) {
        this.notFoundPlace = notFoundPlace;
    }
}
