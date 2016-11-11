package ru.vaadinp.compiler.datamodel;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.MVPImpl;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.api.MVP;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Aleksandr on 05.11.2016.
 */
public class MVPModel<METAMODEL extends MVPMetadataModel> extends ClassDataModel {
    private final METAMODEL metamodel;

    public MVPModel(METAMODEL metamodel, String packageName) {
        super(metamodel.getApiName() + "MVP", packageName);
        this.metamodel = metamodel;

        importClass(Lazy.class);
        importClass(Binds.class);
        importClass(Module.class);

        importClass(Singleton.class);
        importClass(Inject.class);

        importClass(RootMVP.class);

        imports();
    }

    public String getApiName() {
        return metamodel.getApiName();
    }

    public String getViewApiName() {
        return metamodel.getViewApiName();
    }

    public String getViewImplName() {
        return metamodel.getViewImplName();
    }

    public String getPresenterApiName() {
        return metamodel.getPresenterApiName();
    }

    public String getPresenterImplName() {
        return metamodel.getPresenterImplName();
    }

    protected void imports() {
        importClass(MVPImpl.class);
        importClass(PresenterComponent.class);
        importClass(MVP.class);
    }
}
