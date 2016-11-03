package ru.vaadinp.compiler.test.nested;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.vaadinp.annotations.dagger.DefaultPlaceNameToken;
import ru.vaadinp.annotations.dagger.ErrorPlaceNameToken;
import ru.vaadinp.annotations.dagger.NameTokenParts;
import ru.vaadinp.annotations.dagger.NotFoundPlaceNameToken;
import ru.vaadinp.place.error.BaseErrorToken;
import ru.vaadinp.place.notfound.BaseNotFoundToken;
import ru.vaadinp.uri.UriFragmentSource;
import ru.vaadinp.error.ErrorManager;
import ru.vaadinp.place.PlaceManager;
import ru.vaadinp.place.PlaceUtils;
import ru.vaadinp.slot.root.RootMVP;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

import ru.vaadinp.place.BasePlaceManager;
import ru.vaadinp.error.BaseErrorManager;
import ru.vaadinp.uri.PageUriFragmentSource;
import ru.vaadinp.place.error.BaseErrorPlaceMVP;
import ru.vaadinp.place.error.BaseErrorPlacePresenter;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceMVP;
import ru.vaadinp.place.notfound.BaseNotFoundPlacePresenter;
import ru.vaadinp.compiler.test.nested.SimpleNestedMVP;
import ru.vaadinp.compiler.test.nested.SimpleNestedPresenter;

@Module(includes = {
        VaadinPlatformModule.SystemDeclarations.class,
        VaadinPlatformModule.MVPDeclarations.class
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
            RootMVP.Declarations.class,
            BaseErrorPlaceMVP.Declarations.class,
            BaseNotFoundPlaceMVP.Declarations.class,
            SimpleNestedMVP.Declarations.class

    })
    public interface MVPDeclarations {
    }

    private final static Set<String> TOKEN_PARTS = new HashSet<>(); static {
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(BaseErrorToken.DECODED_VAADINP_ERROR));
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(BaseNotFoundToken.DECODED_VAADINP_NOUTFOUND));
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
        return BaseNotFoundToken.DECODED_VAADINP_NOUTFOUND;
    }

    @Provides
    @Singleton
    @NotFoundPlaceNameToken
    static String notFoundPlaceNameToken() {
        return BaseErrorToken.DECODED_VAADINP_ERROR;
    }

    @Provides
    @Singleton
    @ErrorPlaceNameToken
    static String errorPlaceNameToken() {
        return BaseErrorToken.DECODED_VAADINP_ERROR;
    }
}