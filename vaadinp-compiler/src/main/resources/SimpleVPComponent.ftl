<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.AnnotatedGenerateVPComponentClass" -->
package ${packageName};

import dagger.Lazy;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Binds;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

@Singleton
public class ${componentName} extends VPComponent<${presenterImplName}, ${viewImplName}> {

    @Module
    public interface Declarations {
        @Binds
        VPComponent<? extends PresenterComponent<${viewApiName}>, ?> vpComponent(${componentName} vpComponent);

        @Binds
        ${viewApiName} view(${viewImplName} view);

        @Binds
        ${presenterApiName} presenter(${presenterImplName} presenter);
    }

    @Inject
    public ${componentName}(Lazy<${presenterImplName}> lazyPresenterComponent, Lazy<${viewImplName}> lazyView) {
        super(lazyPresenterComponent, lazyView);
    }
}