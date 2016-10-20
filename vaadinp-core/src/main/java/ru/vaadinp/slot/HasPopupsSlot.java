package ru.vaadinp.slot;


import ru.vaadinp.vp.PopupView;
import ru.vaadinp.vp.PresenterComponent;

/**
 * Created by oem on 8/2/16.
 */
public interface HasPopupsSlot {
	void addToPopupSlot(final PresenterComponent<? extends PopupView> child);
	void removeFromPopupSlot(final PresenterComponent<? extends PopupView> child);
}
