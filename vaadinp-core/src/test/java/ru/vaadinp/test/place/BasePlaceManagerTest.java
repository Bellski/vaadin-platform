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
import ru.vaadinp.place.error.BaseErrorPlaceVPComponent;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceVPComponent;
import ru.vaadinp.slot.BaseSlotRevealBus;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.slot.root.RootVPComponent;
import ru.vaadinp.uri.BaseUriFragmentSource;
import ru.vaadinp.uri.UriFragmentSource;
import ru.vaadinp.vp.PlaceVPComponent;

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

	private final BaseSlotRevealBus baseSlotRevealBus = new BaseSlotRevealBus(new HashSet<>());

	private final RootVPComponent rootVPComponent = mockRootVP(baseSlotRevealBus);

	private final PlaceVPComponent<?, ?> defaultVP = (PlaceVPComponent<?, ?>) mockNestedVP(DEFAULT_NAME_TOKEN, baseSlotRevealBus, RootPresenter.ROOT_SLOT, false);
	private final BaseErrorPlaceVPComponent baseErrorVP = mockBaseBaseErrorPlaceVP(baseSlotRevealBus);
	private final BaseNotFoundPlaceVPComponent baseNotFoundPlaceVP = mockBaseNotFoundVP(baseSlotRevealBus);


	final Map<String, PlaceVPComponent<?, ?>> placeByNameToken = new HashMap<>(); {
		placeByNameToken.put(BaseErrorPlaceVPComponent.NAME_TOKEN, baseErrorVP);
		placeByNameToken.put(BaseNotFoundPlaceVPComponent.NAME_TOKEN, baseNotFoundPlaceVP);
		placeByNameToken.put(DEFAULT_NAME_TOKEN, defaultVP);
	}

	private final UriFragmentSource uriFragmentSource = new BaseUriFragmentSource();

	private final ErrorManager errorManager = new BaseErrorManager(BaseErrorPlaceVPComponent.NAME_TOKEN, BaseNotFoundPlaceVPComponent.NAME_TOKEN);

	private final Set<String> nameTokenParts = new HashSet<>(); {
		nameTokenParts.addAll(PlaceUtils.breakIntoNameTokenParts(DefaultPresenterMock.NAME_TOKEN));
		nameTokenParts.addAll(PlaceUtils.breakIntoNameTokenParts(BaseErrorPlaceVPComponent.NAME_TOKEN));
		nameTokenParts.addAll(PlaceUtils.breakIntoNameTokenParts(BaseNotFoundPlaceVPComponent.NAME_TOKEN));
	}

	private final PlaceManager placeManager = new BasePlaceManager(placeByNameToken, nameTokenParts, uriFragmentSource, errorManager, DefaultPresenterMock.NAME_TOKEN);

	@Test
	public void goToDefaultPlaceIfUriTokenIsEmpty() {
		placeManager.revealCurrentPlace();

		assertTrue(defaultVP.getPresenter().isVisible());
	}

	@Test
	public void goToCurrentExistedUriToken() {
		uriFragmentSource.setCurrentUriFragment(DefaultPresenterMock.NAME_TOKEN, false);

		placeManager.revealCurrentPlace();

		assertTrue(defaultVP.getPresenter().isVisible());
	}

	@Test
	public void revealToTheExistedPlace() {
		placeManager.revealPlace(
			new PlaceRequest
				.Builder()
				.nameToken(DefaultPresenterMock.NAME_TOKEN)
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
