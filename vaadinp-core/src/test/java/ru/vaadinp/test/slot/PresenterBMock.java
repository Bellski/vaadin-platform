package ru.vaadinp.test.slot;

import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.View;
import ru.vaadinp.vp.ViewImpl;

/**
 * Created by oem on 10/11/16.
 */
public class PresenterBMock extends NestedPresenter<View> {

	public PresenterBMock() {
		super(new ViewImpl<>(), PresenterAMock.MAIN_SLOT);
	}
}
