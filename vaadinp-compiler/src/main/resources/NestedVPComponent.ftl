<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.GenerateNestedVPComponentModel" -->
package ${packageName};

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.vp.NestedVPComponent;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

<#if presenterComponent.nestedSlotMirror??>
import dagger.Provides;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
</#if>

import ${parent.fqn};

@Singleton
public class ${name} extends NestedVPComponent<${presenterComponent.name}, ${presenterComponent.viewMirror.name}> {

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
    }

    @Inject
    public ${name}(Lazy<${presenterComponent.name}> lazyPresenterComponent,
                            Lazy<${presenterComponent.viewMirror.name}> lazyView,
                            ${parent.name} parent) {

        super(lazyPresenterComponent, lazyView, parent);
    }
}