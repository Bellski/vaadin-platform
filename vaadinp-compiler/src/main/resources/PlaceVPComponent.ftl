<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.AnnotatedGenerateVPComponentClass" -->
package ${packageName};

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.slot.SlotRevealBus;
import ru.vaadinp.vp.PlaceVPComponent;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ${componentName} extends PlaceVPComponent<${presenterImplName}, ${viewImplName}> {

    @Module
    public interface Declarations {
        @Binds
        VPComponent<? extends PresenterComponent<${viewApiName}>, ?> vpComponent(${componentName} vpComponent);

        @Binds
        ${viewApiName} view(${viewImplName} view);

        @Binds
        ${presenterApiName} presenter(${presenterImplName} presenter);

        @IntoMap
        @PlacesMap
        @Binds
        @StringKey(${presenterImplName}.NAME_TOKEN) PlaceVPComponent<?, ?> place(${componentName} vpComponent);

    }

    @Inject
    public ${componentName}(Lazy<${presenterImplName}> lazyPresenterComponent, Lazy<${viewImplName}> lazyView, SlotRevealBus slotRevealBus) {
        super(${presenterImplName}.NAME_TOKEN, lazyPresenterComponent, lazyView, slotRevealBus);
    }
}