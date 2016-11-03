package ru.vaadinp.place;

import ru.vaadinp.vp.api.PlaceMVP;

/**
 * @author bellski
 *
 * PlaceManager отвечает за навигацию по Presenter'ам
 */
public interface PlaceManager {
	PlaceRequest getCurrentPlaceRequest();
	PlaceMVP<?> getCurrentPlace();
	void revealPlace(PlaceRequest request);
	void revealPlace(PlaceRequest request, boolean updateBrowserUrl);

	void revealDefaultPlace();
	void revealCurrentPlace();

	PlaceRequest getDefaultPlaceRequest();
}
