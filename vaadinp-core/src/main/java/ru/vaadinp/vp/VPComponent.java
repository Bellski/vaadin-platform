package ru.vaadinp.vp;

import com.vaadin.server.Resource;
import dagger.Lazy;

/**
 * Created by oem on 10/12/16.
 */
public class VPComponent<PRESENTER extends PresenterComponent<?>, VIEW extends ViewImpl<?>> {
	private String caption;
	private Resource icon;
	private Lazy<PRESENTER> lazyPresenterComponent;
	private Lazy<VIEW> lazyView;

	public VPComponent(Lazy<PRESENTER> lazyPresenterComponent, Lazy<VIEW> lazyView) {
		this.lazyPresenterComponent = lazyPresenterComponent;
		this.lazyView = lazyView;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Resource getIcon() {
		return icon;
	}

	public void setIcon(Resource icon) {
		this.icon = icon;
	}

	public PRESENTER getPresenter() {
		return lazyPresenterComponent.get();
	}

	public VIEW getView() {
		return lazyView.get();
	}
}
