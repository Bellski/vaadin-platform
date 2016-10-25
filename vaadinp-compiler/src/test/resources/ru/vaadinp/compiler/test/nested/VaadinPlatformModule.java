package ru.vaadinp.compiler.test.nested;

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

import ru.vaadinp.place.BasePlaceManager;
import ru.vaadinp.error.BaseErrorManager;
import ru.vaadinp.uri.PageUriFragmentSource;
import ru.vaadinp.place.error.BaseErrorPlaceVPComponent;
import ru.vaadinp.place.error.BaseErrorPlacePresenter;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceVPComponent;
import ru.vaadinp.place.notfound.BaseNotFoundPlacePresenter;
import ru.vaadinp.compiler.test.nested.SimpleNestedVPComponent;
import ru.vaadinp.compiler.test.nested.SimpleNestedPresenter;

@Module(includes = {
        VaadinPlatformModule.SystemDeclarations.class,
        VaadinPlatformModule.VPComponentsDeclarations.class
})
public class VaadinPlatformModule {



    @Module
    public interface SystemDeclarations {
        @Binds
        PlaceManager placeManager(BasePlaceManager placeManager);

        @Binds
        ErrorManager errorManager(BaseErrorManager errorManager);

        @Binds
        UriFragmentSource uriFragmentSource(PageUriFragmentSource uriFragmentSource);
    }

    @Module(includes = {
            RootVPComponent.Declarations.class,
            BaseErrorPlaceVPComponent.Declarations.class,
            BaseNotFoundPlaceVPComponent.Declarations.class,
            SimpleNestedVPComponent.Declarations.class

    })
    public interface VPComponentsDeclarations {
    }

    private final static Set<String> TOKEN_PARTS = new HashSet<>(); static {
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(BaseNotFoundPlacePresenter.NAME_TOKEN));
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(BaseErrorPlacePresenter.NAME_TOKEN));
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
        return BaseNotFoundPlacePresenter.NAME_TOKEN;
    }

    @Provides
    @Singleton
    @NotFoundPlaceNameToken
    static String notFoundPlaceNameToken() {
        return BaseNotFoundPlacePresenter.NAME_TOKEN;
    }

    @Provides
    @Singleton
    @ErrorPlaceNameToken
    static String errorPlaceNameToken() {
        return BaseErrorPlacePresenter.NAME_TOKEN;
    }
}