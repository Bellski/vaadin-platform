<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.AnnotatedGenerateVPComponentClass" -->
package ${packageName};

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.slot.SlotRevealBus;
import ru.vaadinp.vp.NestedVPComponent;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

import javax.inject.Inject;
import javax.inject.Singleton;
<#if hasNestedSlot>
import dagger.Provides;
import dagger.multibindings.IntoSet;
import ru.vaadinp.annotations.dagger.IntoSlotMap;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import java.util.AbstractMap;
import java.util.Map;
</#if>

@Singleton
public class ${componentName} extends NestedVPComponent<${presenterImplName}, ${viewImplName}> {

    @Module
    public interface Declarations {
        @Binds
        VPComponent<? extends PresenterComponent<${viewApiName}>, ?> vpComponent(${componentName} vpComponent);

        @Binds
        ${viewImplName} view(${viewImplName} view);

        @Binds
        ${presenterApiName} presenter(${presenterImplName} presenter);

        <#if hasSlot()>
        @Provides
        @Singleton
        @IntoSlotMap
        @IntoSet
        static Map.Entry<NestedSlot, NestedVPComponent<?, ?>> bindNestedSlot(${componentName} vpComponent) {
            return new AbstractMap.SimpleImmutableEntry<>(${presenterImplName}.${slotName}, vpComponent);
        }

        @Provides
        @Singleton
        @RevealIn(${presenterImplName}.class)
        static NestedSlot nestedSlot() {
            return ${presenterImplName}.${slotName};
        }
        </#if>
    }

    @Inject
    public ${componentName}(Lazy<${presenterImplName}> lazyPresenterComponent, Lazy<${viewImplName}> lazyView, SlotRevealBus slotRevealBus) {
        super(lazyPresenterComponent, lazyView, slotRevealBus);
    }
}