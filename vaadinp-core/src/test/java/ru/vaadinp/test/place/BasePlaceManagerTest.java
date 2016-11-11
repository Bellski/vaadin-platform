package ru.vaadinp.test.place;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.vaadinp.error.BaseErrorManager;
import ru.vaadinp.error.ErrorManager;
import ru.vaadinp.place.BasePlaceManager;
import ru.vaadinp.place.PlaceManager;
import ru.vaadinp.place.PlaceRequest;
import ru.vaadinp.place.PlaceUtils;
import ru.vaadinp.place.error.BaseErrorPlaceMVP;
import ru.vaadinp.place.error.BaseErrorPlaceTokenSet;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceMVP;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceTokenSet;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.test.NestedMVPBuilder;
import ru.vaadinp.uri.BaseUriFragmentSource;
import ru.vaadinp.uri.UriFragmentSource;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.api.PlaceMVP;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static ru.vaadinp.test.MockUtils.*;

/**
 * Created by oem on 10/10/16.
 */
@RunWith(JUnit4.class)
public class BasePlaceManagerTest {
	public final static String DEFAULT_NAME_TOKEN = "!/dummyDefaultPlace";

	private final RootMVP rootVPComponent = mockRootVP();

	private final PlaceMVP<? extends BaseNestedPresenter<?>> defaultVP = new NestedMVPBuilder(rootVPComponent,  rootVPComponent, RootPresenter.ROOT_SLOT)
		.withNameToken(DEFAULT_NAME_TOKEN)
		.buildPlace();

	private final BaseErrorPlaceMVP baseErrorVP = mockBaseBaseErrorPlaceVP(rootVPComponent);
	private final BaseNotFoundPlaceMVP baseNotFoundPlaceVP = mockBaseNotFoundVP(rootVPComponent);

	final Map<String, PlaceMVP<?>> placeByNameToken = new HashMap<>(); {
		placeByNameToken.put(BaseErrorPlaceTokenSet.ENCODED_VAADINPERROR, baseErrorVP);
		placeByNameToken.put(BaseNotFoundPlaceTokenSet.ENCODED_VAADINPNOTFOUND, baseNotFoundPlaceVP);
		placeByNameToken.put(DEFAULT_NAME_TOKEN, defaultVP);
	}

	private final UriFragmentSource uriFragmentSource = new BaseUriFragmentSource();

	private final ErrorManager errorManager = new BaseErrorManager(BaseErrorPlaceTokenSet.ENCODED_VAADINPERROR, BaseNotFoundPlaceTokenSet.ENCODED_VAADINPNOTFOUND);

	private final Set<String> nameTokenParts = new HashSet<>(); {
		nameTokenParts.addAll(PlaceUtils.breakIntoNameTokenParts(DefaultPresenterMockBase.NAME_TOKEN));
		nameTokenParts.addAll(PlaceUtils.breakIntoNameTokenParts(BaseErrorPlaceTokenSet.ENCODED_VAADINPERROR));
		nameTokenParts.addAll(PlaceUtils.breakIntoNameTokenParts(BaseNotFoundPlaceTokenSet.ENCODED_VAADINPNOTFOUND));
	}

	private final PlaceManager placeManager = new BasePlaceManager(placeByNameToken, nameTokenParts, uriFragmentSource, errorManager, DefaultPresenterMockBase.NAME_TOKEN);

	@Test
	public void goToDefaultPlaceIfUriTokenIsEmpty() {
		placeManager.revealCurrentPlace();

		assertTrue(defaultVP.getPresenter().isVisible());
	}

	@Test
	public void goToCurrentExistedUriToken() {
		uriFragmentSource.setCurrentUriFragment(DefaultPresenterMockBase.NAME_TOKEN, false);

		placeManager.revealCurrentPlace();

		assertTrue(defaultVP.getPresenter().isVisible());
	}

	@Test
	public void revealToTheExistedPlace() {
		placeManager.revealPlace(
			new PlaceRequest
				.Builder()
				.nameToken(DefaultPresenterMockBase.NAME_TOKEN)
				.build()
		);

		assertTrue(defaultVP.getPresenter().isVisible());
	}

	@Test
	public void revealToTheNonExistedPlace() {
		placeManager.revealPlace(
			new PlaceRequest
				.Builder()
				.nameToken("!/not-exist")
				.build()
		);

		assertTrue(baseNotFoundPlaceVP.getPresenter().isVisible());
	}

}
