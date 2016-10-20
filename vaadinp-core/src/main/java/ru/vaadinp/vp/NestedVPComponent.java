package ru.vaadinp.vp;

import dagger.Lazy;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.SlotRevealBus;

/**
 * Created by oem on 10/12/16.
 */
public class NestedVPComponent<PRESENTER extends NestedPresenter<?>, VIEW extends ViewImpl<?>> extends VPComponent<PRESENTER, VIEW> {

	private final SlotRevealBus slotRevealBus;

	public NestedVPComponent(Lazy<PRESENTER> lazyPresenterComponent, Lazy<VIEW> lazyView) {
		this(lazyPresenterComponent, lazyView, null);
	}

	public NestedVPComponent(Lazy<PRESENTER> lazyPresenterComponent, Lazy<VIEW> lazyView, SlotRevealBus slotRevealBus) {
		super(lazyPresenterComponent, lazyView);
		this.slotRevealBus = slotRevealBus;
	}

	public void revealInSlot(NestedSlot nestedSlot, NestedPresenter<?> child) {
		if (slotRevealBus != null) {
			slotRevealBus.fireReveal(nestedSlot, child);
		}
	}
}
