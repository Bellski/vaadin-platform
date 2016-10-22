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
<#if hasSlot()>
import dagger.Provides;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
</#if>
import ${parentQualifiedName};

@Singleton
public class ${componentName} extends NestedVPComponent<${presenterImplName}, ${viewImplName}> {

    @Module
    public interface Declarations {
        @Binds
        VPComponent<? extends PresenterComponent<${viewApiName}>, ?> vpComponent(${componentName} vpComponent);

        @Binds
        ${viewApiName} view(${viewImplName} view);

        @Binds
        ${presenterApiName} presenter(${presenterImplName} presenter);

        <#if hasSlot()>
        @Provides
        @Singleton
        @RevealIn(${presenterImplName}.class)
        static NestedSlot nestedSlot() {
            return ${presenterImplName}.${slotName};
        }
        </#if>
    }

    @Inject
    public ${componentName}(Lazy<${presenterImplName}> lazyPresenterComponent,
                            Lazy<${viewImplName}> lazyView,
                            ${parentName} parent) {

        super(lazyPresenterComponent, lazyView, parent);
    }
}