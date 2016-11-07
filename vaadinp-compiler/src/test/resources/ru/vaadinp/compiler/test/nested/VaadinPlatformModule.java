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
import ru.vaadinp.slot.root.RootMVP;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

import ru.vaadinp.place.BasePlaceManager;
import ru.vaadinp.error.BaseErrorManager;
import ru.vaadinp.uri.PageUriFragmentSource;
import ru.vaadinp.place.error.BaseErrorPlaceMVP;
import ru.vaadinp.place.error.BaseErrorPlaceToken;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceMVP;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceToken;
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
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(BaseNotFoundPlaceToken.DECODED_VAADINP_NOTFOUND));
        TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(BaseErrorPlaceToken.DECODED_VAADINP_ERROR));
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
        return BaseNotFoundPlaceToken.ENCODED_VAADINP_NOTFOUND;
    }

    @Provides
    @Singleton
    @NotFoundPlaceNameToken
    static String notFoundPlaceNameToken() {
        return BaseNotFoundPlaceToken.ENCODED_VAADINP_NOTFOUND;
    }

    @Provides
    @Singleton
    @ErrorPlaceNameToken
    static String errorPlaceNameToken() {
        return BaseErrorPlaceToken.ENCODED_VAADINP_ERROR;
    }
}