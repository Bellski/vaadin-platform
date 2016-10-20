package ru.vaadinp.test.slot;

import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.View;
import ru.vaadinp.vp.ViewImpl;

/**
 * Created by oem on 10/11/16.
 */
public class PresenterAMock extends NestedPresenter<View> {

	public static final NestedSlot MAIN_SLOT = new NestedSlot();

	public PresenterAMock() {
		super(new ViewImpl<>(), RootPresenter.ROOT_SLOT);
	}
}
