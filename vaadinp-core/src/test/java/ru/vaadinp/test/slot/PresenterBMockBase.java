package ru.vaadinp.test.slot;

import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.View;
import ru.vaadinp.vp.ViewImpl;

/**
 * Created by oem on 10/11/16.
 */
public class PresenterBMockBase extends BaseNestedPresenter<View> {

	public PresenterBMockBase(NestedSlot revealIn) {
		super(new ViewImpl<>(), revealIn);
	}
}
