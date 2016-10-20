package ru.vaadinp.place;

import ru.vaadinp.Cleanable;

/**
 * @author bellski
 *
 * PlaceManager отвечает за навигацию по Presenter'ам
 */
public interface PlaceManager extends Cleanable {
	PlaceRequest getCurrentPlaceRequest();
	void revealPlace(PlaceRequest request);
	void revealPlace(PlaceRequest request, boolean updateBrowserUrl);

	void revealDefaultPlace();
	void revealCurrentPlace();

	PlaceRequest getDefaultPlaceRequest();
}
