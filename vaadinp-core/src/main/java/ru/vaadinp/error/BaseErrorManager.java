package ru.vaadinp.error;

import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import ru.vaadinp.annotations.dagger.ErroPlaceNameToken;
import ru.vaadinp.annotations.dagger.NotFoundPlaceNameToken;
import ru.vaadinp.place.PlaceManager;
import ru.vaadinp.place.PlaceRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by oem on 10/6/16.
 */

@Singleton
public class BaseErrorManager implements ErrorManager, ErrorHandler {
	public static final Logger logger = Logger.getLogger("BaseErrorManager");

	private PlaceRequest errorTokenPlace;
	private PlaceRequest notFoundTokenPlace;

	private PlaceManager placeManager;

	@Inject
	public BaseErrorManager(@ErroPlaceNameToken String errorTokenName, @NotFoundPlaceNameToken String notFoundTokenName) {
		this.errorTokenPlace = new PlaceRequest
			.Builder()
			.nameToken(errorTokenName)
			.build();

		this.notFoundTokenPlace = new PlaceRequest
			.Builder()
			.nameToken(notFoundTokenName)
			.build();
	}

	@Override
	public void exception(PlaceRequest placeRequest, Exception e) {
		exception(placeRequest.getNameToken(), e);
	}

	@Override
	public void exception(String nameToken, Exception e) {
		logger.log(Level.SEVERE, nameToken, e);
		placeManager.revealPlace(errorTokenPlace, false);
	}

	@Override
	public void error(PlaceRequest placeRequest, String message) {
		logger.log(Level.SEVERE, placeRequest + ": " + message);
	}

	@Override
	public void placeNotFound(String nameToken) {
		logger.log(Level.WARNING, nameToken +  " not found.");
		placeManager.revealPlace(notFoundTokenPlace, false);
	}

	@Override
	public void setPlaceManager(PlaceManager placeManager) {
		this.placeManager = placeManager;
	}

	@Override
	public void error(ErrorEvent event) {
		exception(placeManager.getCurrentPlaceRequest(), (Exception) event.getThrowable());
	}
}
