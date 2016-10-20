package ru.vaadinp.slot;

import ru.vaadinp.vp.PresenterComponent;

public interface IsSlot<PRESENTER extends PresenterComponent<?>> {
	boolean isPopup();
    boolean isRemovable();
}