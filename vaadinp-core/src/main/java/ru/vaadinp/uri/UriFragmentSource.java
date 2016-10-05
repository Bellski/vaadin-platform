package ru.vaadinp.uri;

import ru.vaadinp.Cleanable;
import ru.vaadinp.uri.handlers.UriFragmentChangeHandler;

/**
 * @author bellski
 *
 * Класс отвечает за хранение текущего uri fragment и оповещение PlaceManager об его изменении.
 * Класс нужен для того, что бы можно было подставлять разные реализации в PlaceManager.
 * Например есть стандартный потомок PageUriFragmentSource. Источником события об изменении uri fragment является
 * класс Page из библиотеки Vaadin, но вдруг вам захочится как-то по другому обрабатывать изменение uri fragment.
 * Отнаследуйте этот класс и поставьте аннотацию DefaultFragmentSource, генератор подставит вашу реализацию в PlaceManager.
 */
public class UriFragmentSource implements Cleanable {

	/**
	 * Мягкая ссылка на слушателя об изменении uri fragment
	 */
	private UriFragmentChangeHandler uriFragmentChangeHandler;

	/**
	 * Текущий uri fragment
	 */
	private String currentUriFragment;

	/**
	 * Установить слушателя изменения uri fragment
	 *
	 * @param uriFragmentChangeHandler
	 */
	public void setUriFragmentChangeHandler(UriFragmentChangeHandler uriFragmentChangeHandler) {

		if (this.uriFragmentChangeHandler != null) {
			throw new IllegalStateException("UriFragmentChangeHandler can be set only once");
		}

		this.uriFragmentChangeHandler = uriFragmentChangeHandler;
	}

	/**
	 * Получить текущий uri fragment
	 * @return String - uri fragment
	 */
	public String getCurrentUriFragment() {
		return currentUriFragment;
	}

	/**
	 * Установить текущий uri fragment
	 *
	 * @param currentUriFragment - uri fragment
	 * @param fireEvent - оповещать слушателя. Может быть ситуация когда реакция на изменение uri fragment не требуется
	 */
	protected void setCurrentUriFragment(String currentUriFragment, boolean fireEvent) {
		this.currentUriFragment = currentUriFragment;

		if (fireEvent) {
			fireUriFragmentChange(currentUriFragment);
		}
	}

	/**
	 * Оповестить слушателя события об изменении uri fragment
	 * @param uriFragment
	 */
	protected void fireUriFragmentChange(String uriFragment) {
		if (uriFragmentChangeHandler != null) {
			uriFragmentChangeHandler.onUriFragmentChanged(uriFragment);
		}
	}

	/**
	 * Смотреть Cleanable.clean()
	 */
	@Override
	public void clean() {
		uriFragmentChangeHandler = null;
		currentUriFragment = null;
	}
}
