package ru.vaadinp.test.uri;

import org.junit.Assert;
import org.junit.Test;
import ru.vaadinp.uri.handlers.UriFragmentChangeHandler;
import ru.vaadinp.uri.BaseUriFragmentSource;

/**
 * @author bellski
 *
 * Класс связан с полным тестированием класса UriFragmentSource
 *
 */
public class TestUriFragmentSource {
	private static final String URI_FRAGMENT = "!#/vaadinp-rocks";

	/**
	 * Проверка на изменения uri fragment из внешнего источника.
	 *
	 * Сценарий:
	 * 	Мокаем UriFragmentSource, добавляем метод который меняет uri fragment.
	 * 	Мокаем UriFragmentChangeHandler для того что-бы протестирова изменение uri fragment.
	 * 	Подписываемся на изменение uri fragment.
	 * 	Изменяем uri fragment.
	 *
	 * Тест пройден если TestUriFragmentSource.URI_FRAGMENT и UriFragmentChangeHandlerMock.uriFragment совпадаеют
	 * @throws Exception
	 */
	@Test
	public void testChangeUri() throws Exception {

		class ManualUriFragmentSource extends BaseUriFragmentSource {

			/**
			 * Изменить uri
			 */
			private void changeUri() {
				super.setCurrentUriFragment(URI_FRAGMENT, true);
			}
		}

		class UriFragmentChangeHandlerMock implements UriFragmentChangeHandler {
			private String uriFragment;

			@Override
			public void onUriFragmentChanged(String uriFragment) {
				this.uriFragment = uriFragment;
			}
		}

		final ManualUriFragmentSource uriFragmentSource = new ManualUriFragmentSource();
		final UriFragmentChangeHandlerMock uriFragmentChangeHandlerMock = new UriFragmentChangeHandlerMock();

		uriFragmentSource.setUriFragmentChangeHandler(uriFragmentChangeHandlerMock);

		uriFragmentSource.changeUri();

		Assert.assertEquals(URI_FRAGMENT, uriFragmentChangeHandlerMock.uriFragment);
	}

}
