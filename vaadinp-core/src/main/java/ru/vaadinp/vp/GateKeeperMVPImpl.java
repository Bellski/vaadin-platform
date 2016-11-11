package ru.vaadinp.vp;

import dagger.Lazy;
import ru.vaadinp.security.Gatekeeper;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.api.GateKeeperMVP;
import ru.vaadinp.vp.api.NestedMVP;

/**
 * Created by oem on 11/7/16.
 */
public class GateKeeperMVPImpl<P extends BaseNestedPresenter<?> & Gatekeeper> extends NestedMVPImpl<P> implements GateKeeperMVP {

	public GateKeeperMVPImpl(Lazy<? extends ViewImpl<?>> lazyView,
							 Lazy<P> lazyPresenter,
							 MVPInfo info,
							 RootMVP rootMVP,
							 NestedMVP<? extends BaseNestedPresenter<?>> parent) {

		super(lazyView, lazyPresenter, rootMVP, info, parent);
	}


	@Override
	public boolean attempt() {
		P presenter = getPresenter();
		boolean canAccess = presenter.canAccess();

		if (!canAccess) {
			if (!presenter.isVisible()) {
				presenter.forceReveal();
			}
		}

		return canAccess;
	}
}
