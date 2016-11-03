package ru.vaadinp.uri;

import ru.vaadinp.uri.handlers.UriFragmentChangeHandler;

/**
 * @author bellski
 *
 * Системный интерфейс. Используется для взаимодействия с {@link PlaceManager}
 * Если нужна своя реализация для взаимодействия с uri и {@link PlaceManager} реализуйте этот интерфейс либо
 * {@link BaseUriFragmentSource}
 * {@link PlaceUriFragmentSource}
 */
public interface UriFragmentSource extends Cleanable {

	/**
	 * Установить слушателя изменения uri fragment
	 *
	 * @param uriFragmentChangeHandler
	 */
	void setUriFragmentChangeHandler(UriFragmentChangeHandler uriFragmentChangeHandler);

	/**
	 * Получить текущий uri fragment
	 * @return String - uri fragment
	 */
	String getCurrentUriFragment();

	/**
	 * Установить текущий uri fragment
	 *
	 * @param currentUriFragment - uri fragment
	 * @param fireEvent - оповещать слушателя. Может быть ситуация когда реакция на изменение uri fragment не требуется
	 */
	void setCurrentUriFragment(String currentUriFragment, boolean fireEvent);
}
