<#-- @ftlvariable name="" type="ru.vaadinp.compiler.processors.GenerateVPComponentModel" -->
package ${packageName};

import dagger.Lazy;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Binds;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

@Singleton
public class ${name} extends VPComponent<${presenterComponent.name}, ${presenterComponent.viewMirror.name}> {

    @Module
    public interface Declarations {
        @Binds
        VPComponent<? extends PresenterComponent<${presenterComponent.apiMirror.viewApiName}>, ?> vpComponent(${name} vpComponent);

        @Binds
        ${presenterComponent.apiMirror.viewApiName} view(${presenterComponent.viewMirror.name} view);

        @Binds
        ${presenterComponent.apiMirror.presenterApiName} presenter(${presenterComponent.name} presenter);
    }

    @Inject
    public ${name}(Lazy<${presenterComponent.name}> lazyPresenterComponent, Lazy<${presenterComponent.viewMirror.name}> lazyView) {
        super(lazyPresenterComponent, lazyView);
    }
}