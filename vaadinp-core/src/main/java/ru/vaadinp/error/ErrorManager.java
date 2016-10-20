package ru.vaadinp.error;

import ru.vaadinp.place.PlaceManager;
import ru.vaadinp.place.PlaceRequest;

/**
 * Created by oem on 10/6/16.
 */
public interface ErrorManager {
	void exception(PlaceRequest placeRequest, Exception e);
	void exception(String nameToken, Exception e);
	void error(PlaceRequest placeRequest, String message);
	void placeNotFound(String nameToken);
	void setPlaceManager(PlaceManager placeManager);
}
