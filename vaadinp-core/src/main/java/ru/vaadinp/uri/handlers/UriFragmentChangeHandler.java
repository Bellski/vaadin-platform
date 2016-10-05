package ru.vaadinp.uri.handlers;

/**
 * @author bellski
 *
 * Слушатель оповещает об изменении uri fragment
 * Используется для взаимодействия с PlaceManager
 */
public interface UriFragmentChangeHandler {

	/**
	 * Оповещает об изменении uri fragment
	 * @param uriFragment
	 */
	void onUriFragmentChanged(String uriFragment);
}
