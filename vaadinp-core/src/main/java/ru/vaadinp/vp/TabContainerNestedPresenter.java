package ru.vaadinp.vp;

import ru.vaadinp.slot.NestedSlot;

/**
 * Created by oem on 10/13/16.
 */
public class TabContainerNestedPresenter<VIEW extends View & TabContainer> extends NestedPresenter<VIEW>  {

	public TabContainerNestedPresenter(VIEW view, NestedSlot nestedSlot) {
		super(view, nestedSlot);
	}

	@Override
	protected void onReveal() {
		if (getVpComponent().getTabs() != null) {
			for (TabPlaceVPComponent<?, ?, ?> tabPlaceVPComponent : getVpComponent().getTabs()) {
				getView().addTab(tabPlaceVPComponent.getTab());
			}
		}
	}

	@Override
	public TabContainerVPComponent<? extends NestedPresenter<VIEW>, ?> getVpComponent() {
		return (TabContainerVPComponent<? extends NestedPresenter<VIEW>, ?>) super.getVpComponent();
	}
}
