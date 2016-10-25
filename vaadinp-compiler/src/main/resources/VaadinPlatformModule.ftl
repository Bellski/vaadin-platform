<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.vaadinpmodule.GenerateVaadinPlatformModuleModel" -->
package ${packageName};

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.vaadinp.annotations.dagger.DefaultPlaceNameToken;
import ru.vaadinp.annotations.dagger.ErrorPlaceNameToken;
import ru.vaadinp.annotations.dagger.NameTokenParts;
import ru.vaadinp.annotations.dagger.NotFoundPlaceNameToken;
import ru.vaadinp.uri.UriFragmentSource;
import ru.vaadinp.error.ErrorManager;
import ru.vaadinp.place.PlaceManager;
import ru.vaadinp.place.PlaceUtils;
import ru.vaadinp.slot.root.RootVPComponent;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

import ${placeManager.fqn};
import ${errorManager.fqn};
import ${uriFragmentSource.fqn};
import ${errorPlace.fqn};
import ${errorPlace.presenterComponent.fqn};
import ${notFoundPlace.fqn};
import ${notFoundPlace.presenterComponent.fqn};
<#list vpComponents as vpComponent>
import ${vpComponent.fqn};
import ${vpComponent.presenterComponent.fqn};
</#list>
<#list vpNestedComponents as vpNestedComponent>
import ${vpNestedComponent.fqn};
import ${vpNestedComponent.presenterComponent.fqn};
</#list>

@Module(includes = {
        VaadinPlatformModule.SystemDeclarations.class,
        VaadinPlatformModule.VPComponentsDeclarations.class
})
public class VaadinPlatformModule {

    @Module
    public interface SystemDeclarations {
        @Binds
        PlaceManager placeManager(${placeManager.name} placeManager);

        @Binds
        ErrorManager errorManager(${errorManager.name} errorManager);

        @Binds
        UriFragmentSource uriFragmentSource(${uriFragmentSource.name} uriFragmentSource);
    }

    @Module(includes = {
            RootVPComponent.Declarations.class,
            ${errorPlace.name}.Declarations.class,
            ${notFoundPlace.name}.Declarations.class,
            ${vpNestedComponents?join(",")}${(vpComponents?has_content)?string(",","")}
            ${vpComponents?join("?")}
    })
    public interface VPComponentsDeclarations {
    }

    private final static Set<String> TOKEN_PARTS = new HashSet<>(); static {
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(${notFoundPlace.presenterComponent.name}.NAME_TOKEN));
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(${errorPlace.presenterComponent.name}.NAME_TOKEN));
        <#list vpNestedComponents as vpNestedComponent>
        <#if vpNestedComponent.presenterComponent.nameToken??>
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(${vpNestedComponent.presenterComponent.name}.NAME_TOKEN));
        </#if>
        </#list>
    }


    @Provides
    @Singleton
    @NameTokenParts
    static Set<String> tokenParts() {
        return VaadinPlatformModule.TOKEN_PARTS;
    }

    @Provides
    @Singleton
    @DefaultPlaceNameToken
    static String defaultPlaceNameToken() {
        return ${defaultPlace.presenterComponent.name}.NAME_TOKEN;
    }

    @Provides
    @Singleton
    @NotFoundPlaceNameToken
    static String notFoundPlaceNameToken() {
        return ${notFoundPlace.presenterComponent.name}.NAME_TOKEN;
    }

    @Provides
    @Singleton
    @ErrorPlaceNameToken
    static String errorPlaceNameToken() {
        return ${errorPlace.presenterComponent.name}.NAME_TOKEN;
    }
}