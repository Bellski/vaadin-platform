<#-- @ftlvariable name="" type="ru.vaadinp.compiler.model.PlaceVPComponentModel" -->
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
public class ${className} extends PlaceVPComponent<${presenterName}, ${viewName}> {

    @Module
    public interface Declarations {
    @Binds
    VPComponent<? extends PresenterComponent<${viewApiClassName}>, ?> vpComponent(${className} vpComponent);

    @Binds
    ${viewApiClassName} view(${viewName} view);

    @Binds
    ${presenterApiClassName} presenter(${presenterName} presenter);

    @IntoMap
    @PlacesMap
    @Binds
    @StringKey(${presenterName}.NAME_TOKEN)
        PlaceVPComponent<?, ?> place(${className} vp);
    }

    @Inject
    public SimplePlaceVPComponent(Lazy<${presenterName}> lazyPresenterComponent, Lazy<${viewName}> lazyView, SlotRevealBus slotRevealBus) {
        super(${presenterName}.NAME_TOKEN, lazyPresenterComponent, lazyView, slotRevealBus);
    }
}