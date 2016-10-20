package ru.vaadinp.vp;

import dagger.Lazy;

/**
 * Created by oem on 10/13/16.
 */
public class TabPlaceVPComponent<PRESENTER extends NestedPresenter<?>, TAB_CONTAINER extends TabContainerVPComponent<?, ?>, VIEW extends ViewImpl<?>> extends PlaceVPComponent<PRESENTER, VIEW> {
	private Tab tab;

	public TabPlaceVPComponent(String nameToken, Lazy<PRESENTER> lazyPresenterComponent, TAB_CONTAINER tabContainer, Lazy<VIEW> lazyView) {
		this(nameToken, null, null, lazyPresenterComponent, tabContainer, lazyView);
	}

	public TabPlaceVPComponent(String nameToken,
							   String[] parameterNames,
							   int[] parameterIndexes,
							   Lazy<PRESENTER> lazyPresenterComponent,
							   TAB_CONTAINER tabContainer,
							   Lazy<VIEW> lazyView) {
		super(nameToken, parameterNames, parameterIndexes, lazyPresenterComponent, lazyView);

		tabContainer.addTab(this);
	}

	public Tab getTab() {
		if (tab == null) {
			tab = new Tab(getCaption(), getIcon());
		}

		return tab;
	}
}
