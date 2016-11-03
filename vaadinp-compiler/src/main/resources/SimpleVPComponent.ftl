<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.GenerateMVPModel" -->
package ${packageName};

import dagger.Lazy;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Binds;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.MVPImpl;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.api.MVP;

@Singleton
public class ${name} extends MVPImpl<${presenterComponent.name}> {

    @Module
    public interface Declarations {
        @Binds
        MVP<? extends PresenterComponent<${presenterComponent.apiMirror.viewApiName}>> mvp(${name} mvp);

        @Binds
        ${presenterComponent.apiMirror.viewApiName} view(${presenterComponent.viewMirror.name} view);

        @Binds
        ${presenterComponent.apiMirror.presenterApiName} presenter(${presenterComponent.name} presenter);
    }

    @Inject
    public ${name}(Lazy<${presenterComponent.viewMirror.name}> lazyView, Lazy<${presenterComponent.name}> lazyPresenter, RootMVP rootMVP) {
        super(lazyView, lazyPresenter, rootMVP);
    }
}