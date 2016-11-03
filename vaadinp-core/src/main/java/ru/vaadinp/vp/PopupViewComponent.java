package ru.vaadinp.vp;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import ru.vaadinp.vp.api.Presenter;

/**
 * Created by oem on 10/11/16.
 */
public class PopupViewComponent<PRESENTER extends Presenter>  extends ViewImpl<PRESENTER> implements PopupView {

	private UI ui;

	public PopupViewComponent(UI ui) {
		this.ui = ui;
	}

	protected void initWindow(Window component) {
		super.initComponent(component);
	}

	@Override
	public void hide() {
		ui.addWindow(asComponent());
	}

	@Override
	public void show() {
		ui.removeWindow(asComponent());
	}

	@Override
	public Window asComponent() {
		return (Window) super.asComponent();
	}
}
