package ru.vaadinp.vp;

import dagger.Lazy;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.SlotRevealBus;

/**
 * Created by oem on 10/12/16.
 */
public class NestedVPComponent<PRESENTER extends NestedPresenter<?>, VIEW extends ViewImpl<?>> extends VPComponent<PRESENTER, VIEW> {

	private final NestedVPComponent<?, ?> parent;

	public NestedVPComponent(Lazy<PRESENTER> lazyPresenterComponent, Lazy<VIEW> lazyView, NestedVPComponent<?, ?> parent) {
		super(lazyPresenterComponent, lazyView);
		this.parent = parent;
	}

	public void revealInSlot(NestedSlot nestedSlot, NestedPresenter<?> child) {
		parent.getPresenter().forceReveal();
		parent.getPresenter().setInSlot(nestedSlot, child);
	}
}
