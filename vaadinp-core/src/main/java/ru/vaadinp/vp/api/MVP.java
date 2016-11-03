package ru.vaadinp.vp.api;

import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.MVPInfo;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.View;

/**
 * Created by oem on 10/26/16.
 */
public interface MVP<P extends PresenterComponent<?>> {
	Object getModel();
	View getView();
	P getPresenter();
	MVPInfo getInfo();
	RootMVP getRootMVP();
}
