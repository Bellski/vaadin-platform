package ru.vaadinp.compiler.test.gatekeeper.gk;

import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.compiler.test.gatekeeper.ApplicationPresenter;
import ru.vaadinp.place.PlaceManager;
import ru.vaadinp.security.Gatekeeper;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.BaseNestedPresenter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/11/16.
 */
@Singleton
@GenerateMVP
public class TestGateKeeperPresenter extends BaseNestedPresenter<TestGateKeeper.View> implements TestGateKeeper.Presenter, Gatekeeper {

	private final PlaceManager placeManager;
	boolean access = false;

	@Inject
	public TestGateKeeperPresenter(TestGateKeeper.View view, @RevealIn(ApplicationPresenter.class) NestedSlot inSlot, PlaceManager placeManager) {
		super(view, inSlot);
		this.placeManager = placeManager;
	}

	@Override
	public boolean canAccess() {
		return access;
	}

	@Override
	public void access() {
		this.access = true;
		placeManager.revealCurrentPlace();
	}
}
