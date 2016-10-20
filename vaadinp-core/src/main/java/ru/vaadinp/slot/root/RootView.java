package ru.vaadinp.slot.root;

import com.vaadin.ui.UI;
import ru.vaadinp.vp.View;
import ru.vaadinp.vp.ViewImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/10/16.
 */
@Singleton
public class RootView extends ViewImpl<RootPresenter> implements View {
	private UI ui;

	@Inject
	public RootView(UI ui) {
		this.ui = ui;

		bindSlot(RootPresenter.ROOT_SLOT, ui);
	}

}
