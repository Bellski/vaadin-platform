package ru.vaadinp.vp.api;

import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.BaseNestedPresenter;

import java.util.Set;

/**
 * Created by oem on 10/26/16.
 */
public interface NestedMVP<P extends BaseNestedPresenter<?>> extends MVP<P> {
	void revealInSlot(NestedSlot nestedSlot, BaseNestedPresenter<?> child);
	Set<NestedMVP<? extends BaseNestedPresenter<?>>> getChildren();
	void addChild(NestedMVP<?> child);
}
