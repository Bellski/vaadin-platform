<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.GenerateNestedMVPModel" -->
package ${packageName};

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.vp.PlaceVPComponent;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

import ${parent.fqn};

@Singleton
public class ${name} extends PlaceVPComponent<${presenterComponent.name}, ${presenterComponent.viewMirror.name}> {

    @Module
    public interface Declarations {
        @Binds
        VPComponent<? extends PresenterComponent<${presenterComponent.apiMirror.viewApiName}>, ?> vpComponent(${name} vpComponent);

        @Binds
        ${presenterComponent.apiMirror.viewApiName} view(${presenterComponent.viewMirror.name} view);

        @Binds
        ${presenterComponent.apiMirror.presenterApiName} presenter(${presenterComponent.name} presenter);

        <#if presenterComponent.nestedSlotMirror??>
        @Provides
        @Singleton
        @RevealIn(${presenterComponent.name}.class)
        static NestedSlot nestedSlot() {
            return ${presenterComponent.name}.${presenterComponent.nestedSlotMirror.name};
        }
        </#if>

        @IntoMap
        @PlacesMap
        @Binds
        @StringKey(${presenterComponent.name}.NAME_TOKEN) PlaceVPComponent<?, ?> place(${name} vpComponent);

    }

    @Inject
    public ${name}(Lazy<${presenterComponent.name}> lazyPresenter,
                            Lazy<${presenterComponent.viewMirror.name}> lazyView,
                            ${parent.name} parent) {

        super(${presenterComponent.name}.NAME_TOKEN, lazyPresenter, lazyView, parent);
    }
}