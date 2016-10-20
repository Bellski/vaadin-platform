package ru.vaadinp.test;

import dagger.Lazy;
import ru.vaadinp.slot.SlotRevealBus;
import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.NestedVPComponent;
import ru.vaadinp.vp.ViewImpl;

/**
 * Created by oem on 10/19/16.
 */
public class NestedVPComponentMock<PRESENTER extends NestedPresenter<?>, VIEW extends ViewImpl<?>> extends NestedVPComponent<PRESENTER, VIEW> {
	public NestedVPComponentMock(Lazy<PRESENTER> lazyPresenterComponent, Lazy<VIEW> lazyView) {
		super(lazyPresenterComponent, lazyView);
	}

	public NestedVPComponentMock(Lazy<PRESENTER> lazyPresenterComponent, Lazy<VIEW> lazyView, SlotRevealBus slotRevealBus) {
		super(lazyPresenterComponent, lazyView, slotRevealBus);
	}

	@Override
	public PRESENTER getPresenter() {
		final PRESENTER presenter = super.getPresenter();

		NestedVPComponentMock vp = NestedVPComponentMock.this;

		presenter.setVpComponent(vp);

		return presenter;
	}

}
