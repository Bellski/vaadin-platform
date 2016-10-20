package ru.vaadinp.slot;

import ru.vaadinp.vp.PopupView;
import ru.vaadinp.vp.PresenterComponent;

/**
 * Created by oem on 8/2/16.
 */
public class PopupSlot<PRESENTER extends PresenterComponent<? extends PopupView>> extends MultiSlot<PRESENTER> {

	@Override
	public boolean isPopup() {
		return true;
	}
}
