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
import ru.vaadinp.slot.root.RootMVP;

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
        VaadinPlatformModule.MVPDeclarations.class
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
            RootMVP.Declarations.class,
            ${errorPlace.name}.Declarations.class,
            ${notFoundPlace.name}.Declarations.class,
            ${vpNestedComponents?join(",")}${(vpComponents?has_content)?string(",","")}
            ${vpComponents?join("?")}
    })
    public interface MVPDeclarations {
    }

    private final static Set<String> TOKEN_PARTS = new HashSet<>(); static {
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(${notFoundPlace.presenterComponent.notFoundToken.encodedNameTokenConstantName}));
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(${errorPlace.presenterComponent.errorToken.encodedNameTokenConstantName}));
        <#list vpNestedComponents as vpNestedComponent>
        <#if vpNestedComponent.presenterComponent.tokenModelList?has_content>
        <#list vpNestedComponent.presenterComponent.tokenModelList as tokenModel>
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(${tokenModel.decodedNameTokenConstantName}));
        </#list>
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
        return ${defaultPlace.presenterComponent.defaultToken.encodedNameTokenConstantName};
    }

    @Provides
    @Singleton
    @NotFoundPlaceNameToken
    static String notFoundPlaceNameToken() {
        return ${notFoundPlace.presenterComponent.notFoundToken.encodedNameTokenConstantName};
    }

    @Provides
    @Singleton
    @ErrorPlaceNameToken
    static String errorPlaceNameToken() {
        return ${errorPlace.presenterComponent.errorToken.encodedNameTokenConstantName};
    }
}