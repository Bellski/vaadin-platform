package ru.vaadinp.vp;

import dagger.Lazy;
import ru.vaadinp.security.Gatekeeper;
import ru.vaadinp.slot.SlotRevealBus;

/**
 * Created by oem on 10/12/16.
 */
public class PlaceVPComponent<PRESENTER extends NestedPresenter<?>, VIEW extends ViewImpl<?>> extends NestedVPComponent<PRESENTER, VIEW> {
	private final String nameToken;
	private final String[] parameterNames;
	private final int[] parameterIndexes;

	private Lazy<Gatekeeper> lazyGatekeeper;

	public PlaceVPComponent(String nameToken,
							Lazy<PRESENTER> lazyPresenterComponent,
							Lazy<VIEW> lazyView,
							NestedVPComponent<?, ?> parent) {

		this(nameToken, null, null, lazyPresenterComponent, lazyView, parent);
	}


	public PlaceVPComponent(String nameToken,
							String[] parameterNames,
							int[] parameterIndexes,
							Lazy<PRESENTER> lazyPresenterComponent,
							Lazy<VIEW> lazyView,
							NestedVPComponent<?, ?> parent) {

		super(lazyPresenterComponent, lazyView, parent);

		this.nameToken = nameToken;
		this.parameterNames = parameterNames;
		this.parameterIndexes = parameterIndexes;
	}

	public String getNameToken() {
		return nameToken;
	}

	public String[] getParameterNames() {
		return parameterNames;
	}

	public int[] getParameterIndexes() {
		return parameterIndexes;
	}


	public boolean hasParameters() {
		return parameterIndexes != null;
	}

	public Lazy<Gatekeeper> getLazyGatekeeper() {
		return lazyGatekeeper;
	}

	public void setLazyGatekeeper(Lazy<Gatekeeper> lazyGatekeeper) {
		this.lazyGatekeeper = lazyGatekeeper;
	}

	public boolean isSecured() {
		return this.lazyGatekeeper != null;
	}

	@Override
	public String toString() {
		return nameToken;
	}
}
