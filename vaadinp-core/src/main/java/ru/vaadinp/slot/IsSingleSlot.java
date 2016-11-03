package ru.vaadinp.slot;

import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.api.Presenter;

public interface IsSingleSlot<P extends PresenterComponent<?> & Presenter> extends IsSlot<P> {

}