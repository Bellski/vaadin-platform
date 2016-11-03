package ru.vaadinp.slot;

import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.api.Presenter;

public interface IsSlot<P extends PresenterComponent<?> & Presenter> {
	boolean isPopup();
    boolean isRemovable();
}