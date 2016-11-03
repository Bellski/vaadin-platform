package ru.vaadinp.vp.api;

import ru.vaadinp.IsComponent;
import ru.vaadinp.slot.IsSlot;
import ru.vaadinp.vp.PresenterComponent;

/**
 * Created by oem on 10/7/16.
 */
public interface Presenter extends IsComponent {
	void injectMVP(MVP<? extends PresenterComponent<?>> mvp);

	boolean isVisible();

	IsSlot<?> getInSlot();

	boolean isPopup();

	MVP<? extends PresenterComponent<?>> getMVP();
}
