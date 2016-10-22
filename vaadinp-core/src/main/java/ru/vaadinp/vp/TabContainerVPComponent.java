package ru.vaadinp.vp;

import dagger.Lazy;
import ru.vaadinp.slot.SlotRevealBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oem on 10/12/16.
 */
public class TabContainerVPComponent<PRESENTER extends NestedPresenter<?>, VIEW extends ViewImpl<?>> extends PlaceVPComponent<PRESENTER, VIEW> {
	private List<TabPlaceVPComponent<?, ?, ?>> tabs;

	public TabContainerVPComponent(String nameToken,
								   Lazy<PRESENTER> lazyPresenterComponent,
								   Lazy<VIEW> lazyView,
								   NestedVPComponent<?, ?> parent) {

		super(nameToken, lazyPresenterComponent, lazyView, parent);
	}


	public TabContainerVPComponent(String nameToken,
								   String[] parameterNames,
								   int[] parameterIndexes,
								   Lazy<PRESENTER> lazyPresenterComponent,
								   Lazy<VIEW> lazyView,
								   NestedVPComponent<?, ?> parent) {

		super(nameToken, parameterNames, parameterIndexes, lazyPresenterComponent, lazyView, parent);
	}


	public void addTab(TabPlaceVPComponent<?, ?, ?> tabPlaceVPComponent) {
		if (tabs == null) {
			tabs = new ArrayList<>();
		}

		tabs.add(tabPlaceVPComponent);
	}

	public List<TabPlaceVPComponent<?, ?, ?>> getTabs() {
		return tabs;
	}
}
