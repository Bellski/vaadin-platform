package ru.vaadinp.uri.handlers;

import ru.vaadinp.place.PlaceManager;

/**
 * @author bellski
 *
 * Слушатель оповещает об изменении uri fragment
 * Используется для взаимодействия с {@link PlaceManager}
 */
public interface UriFragmentChangeHandler {

	/**
	 * Оповещает об изменении uri fragment
	 * @param uriFragment
	 */
	void onUriFragmentChanged(String uriFragment);
}
