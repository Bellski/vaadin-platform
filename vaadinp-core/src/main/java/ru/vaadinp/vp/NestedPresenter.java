package ru.vaadinp.vp;

import ru.vaadinp.place.PlaceRequest;
import ru.vaadinp.slot.NestedSlot;

/**
 * Created by oem on 10/7/16.
 */
public class NestedPresenter<VIEW extends View> extends PresenterComponent<VIEW> {

	private final NestedSlot nestedSlot;

	public NestedPresenter(VIEW view, NestedSlot nestedSlot) {
		super(view);

		this.nestedSlot = nestedSlot;
	}

	public void forceReveal() {
		if (isVisible()) {
			return;
		}

		getVpComponent().revealInSlot(nestedSlot, this);
	}

	public boolean useManualReveal() {
		return false;
	}

	public void prepareFromRequest(PlaceRequest request) {

	}

	@Override
	public NestedVPComponent<? extends NestedPresenter<VIEW>, ?> getVpComponent() {
		return (NestedVPComponent<? extends NestedPresenter<VIEW>, ?>) super.getVpComponent();
	}

}
