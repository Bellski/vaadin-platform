package ru.vaadinp.vp;

import ru.vaadinp.place.PlaceRequest;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.api.MVP;
import ru.vaadinp.vp.api.NestedMVP;
import ru.vaadinp.vp.api.NestedPresenter;

import javax.inject.Inject;

/**
 * Created by oem on 10/7/16.
 */
public class BaseNestedPresenter<VIEW extends View> extends PresenterComponent<VIEW> implements NestedPresenter {

	private final NestedSlot nestedSlot;

	public BaseNestedPresenter(VIEW view) {
		this(view, null);
	}

	public BaseNestedPresenter(VIEW view, NestedSlot nestedSlot) {
		super(view);

		this.nestedSlot = nestedSlot;

	}

	@Override
	public void forceReveal() {
		if (isVisible()) {
			return;
		}

		getMVP().revealInSlot(nestedSlot, this);
	}

	public boolean useManualReveal() {
		return false;
	}

	public void prepareFromRequest(PlaceRequest request) {

	}

	@Inject
	public void injectMVP(NestedMVP<? extends BaseNestedPresenter<VIEW>> mvp) {
		super.injectMVP(mvp);
	}

	@Override
	public void injectMVP(MVP<? extends PresenterComponent<?>> mvp) {
		super.injectMVP(mvp);
	}

	@Override
	public NestedMVP<? extends BaseNestedPresenter<?>> getMVP() {
		return (NestedMVP<? extends BaseNestedPresenter<?>>) super.getMVP();
	}
}
