package ru.vaadinp.uri;

import com.vaadin.server.Page;
import ru.vaadinp.Cleanable;
import ru.vaadinp.place.PlaceManager;
import ru.vaadinp.uri.handlers.UriFragmentChangeHandler;

/**
 * @author bellski
 *
 * Класс отвечает за хранение текущего uri fragment и оповещение PlaceManager об его изменении.
 * Например есть стандартный потомок {@link PageUriFragmentSource}. Источником события об изменении uri fragment является
 * класс {@link Page} из библиотеки Vaadin, но вдруг вам захочится как-то по другому обрабатывать изменение uri fragment.
 * Отнаследуйте этот класс и поставьте аннотацию {@link DefaultFragmentSource}, генератор подставит вашу реализацию в
 * {@link PlaceManager}.
 */
public class BaseUriFragmentSource implements UriFragmentSource, Cleanable {

	/**
	 * Слушатель изменения uri fragment
	 */
	private UriFragmentChangeHandler uriFragmentChangeHandler;

	/**
	 * Текущий uri fragment
	 */
	private String currentUriFragment;


	@Override
	public void setUriFragmentChangeHandler(UriFragmentChangeHandler uriFragmentChangeHandler) {

		if (this.uriFragmentChangeHandler != null) {
			throw new IllegalStateException("UriFragmentChangeHandler can be set only once");
		}

		this.uriFragmentChangeHandler = uriFragmentChangeHandler;
	}


	@Override
	public String getCurrentUriFragment() {
		return currentUriFragment;
	}


	@Override
	public void setCurrentUriFragment(String currentUriFragment, boolean fireEvent) {
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

	@Override
	public void clean() {
		uriFragmentChangeHandler = null;
		currentUriFragment = null;
	}
}
