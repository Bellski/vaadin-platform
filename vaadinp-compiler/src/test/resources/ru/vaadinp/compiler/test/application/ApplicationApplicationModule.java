package ru.vaadinp.compiler.test.application;

import dagger.Binds;
import ru.vaadinp.annotations.dagger.NameTokenParts;
import ru.vaadinp.place.BasePlaceManager;
import javax.inject.Singleton;
import java.util.HashSet;
import ru.vaadinp.error.ErrorManager;
import dagger.Module;
import ru.vaadinp.error.BaseErrorManager;
import ru.vaadinp.uri.PageUriFragmentSource;
import ru.vaadinp.annotations.dagger.DefaultPlaceNameToken;
import ru.vaadinp.slot.root.RootMVP;
import dagger.Provides;
import ru.vaadinp.compiler.test.application.home.HomeMVP;
import ru.vaadinp.annotations.dagger.ErrorPlaceNameToken;
import ru.vaadinp.place.PlaceUtils;
import java.util.Set;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceMVP;
import ru.vaadinp.place.error.BaseErrorPlaceMVP;
import ru.vaadinp.place.PlaceManager;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceTokenSet;
import ru.vaadinp.annotations.dagger.NotFoundPlaceNameToken;
import ru.vaadinp.uri.UriFragmentSource;
import ru.vaadinp.place.error.BaseErrorPlaceTokenSet;
import ru.vaadinp.compiler.test.application.home.HomeTokenSet;

/**
 * Created by oem on 11/7/16.
 */
@Module(
	includes = {
		ApplicationApplicationModule.SystemDeclarations.class,
		ApplicationApplicationModule.MVPDeclarations.class
	}
)
public class ApplicationApplicationModule {
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
		ApplicationMVP.Declarations.class,
		HomeMVP.Declarations.class,
		BaseErrorPlaceMVP.Declarations.class,
		BaseNotFoundPlaceMVP.Declarations.class
	})
	public interface MVPDeclarations {
	}

	private static final Set<String> TOKEN_PARTS = new HashSet<>(); static {
		TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(HomeTokenSet.DECODED_HOME));
		TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(BaseErrorPlaceTokenSet.DECODED_VAADINPERROR));
		TOKEN_PARTS.addAll(PlaceUtils.breakIntoNameTokenParts(BaseNotFoundPlaceTokenSet.DECODED_VAADINPNOTFOUND));
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
		return HomeTokenSet.ENCODED_HOME;
	}

	@Provides
	@Singleton
	@NotFoundPlaceNameToken
	static String notFoundPlaceNameToken() {
		return BaseNotFoundPlaceTokenSet.ENCODED_VAADINPNOTFOUND;
	}

	@Provides
	@Singleton
	@ErrorPlaceNameToken
	static String errorPlaceNameToken() {
		return BaseErrorPlaceTokenSet.ENCODED_VAADINPERROR;
	}
}
