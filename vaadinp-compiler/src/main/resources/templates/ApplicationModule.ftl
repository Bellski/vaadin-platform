<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.ApplicationModuleModel" -->
package ${packageName};

<#list imports as import>
import ${import};
</#list>

@Module(includes = {
    ${name}.SystemDeclarations.class,
    ${name}.MVPDeclarations.class
})
public class ${name} {
    @Module
    public interface SystemDeclarations {
        @Binds
        PlaceManager placeManager(${placeManagerModel.name} placeManager);

        @Binds
        ErrorManager errorManager(${errorManagerModel.name} errorManager);

        @Binds
        UriFragmentSource uriFragmentSource(${uriFragmentSourceModel.name} uriFragmentSource);
    }

    @Module(includes = {
        RootMVP.Declarations.class,
        ${getMVPDeclarations()?join(",\n\t\t")}
    })
    public interface MVPDeclarations {

    }

    private final static Set<String> TOKEN_PARTS = new HashSet<>(); static {
        <#list tokenModels as tokenModel>
            TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(${tokenModel.decodedNameTokenConstantName}));
        </#list>
    }

    @Provides
    @Singleton
    @NameTokenParts
    static Set<String> tokenParts() {
        return TOKEN_PARTS;
    }

    @Provides
    @Singleton
    @DefaultPlaceNameToken
    static String defaultPlaceNameToken() {
        return ${defaultMVPModel.tokenSetModel.defaultTokenModel.encodedNameTokenConstantName};
    }

    @Provides
    @Singleton
    @NotFoundPlaceNameToken
    static String notFoundPlaceNameToken() {
        return ${notFoundMVPModel.tokenSetModel.notFoundTokenModel.encodedNameTokenConstantName};
    }

    @Provides
    @Singleton
    @ErrorPlaceNameToken
    static String errorPlaceNameToken() {
        return ${errorMVPModel.tokenSetModel.errorTokenModel.encodedNameTokenConstantName};
    }
}