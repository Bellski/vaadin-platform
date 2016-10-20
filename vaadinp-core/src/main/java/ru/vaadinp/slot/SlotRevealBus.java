package ru.vaadinp.slot;

import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.NestedVPComponent;

public interface SlotRevealBus {
	void registerSlot(NestedSlot nestedSlot, NestedVPComponent<?, ?> vpComponent);
	void fireReveal(NestedSlot slot, NestedPresenter<?> child);
}