package ru.vaadinp.vp;

import ru.vaadinp.IsComponent;
import ru.vaadinp.vp.api.Presenter;

/**
 * Created by oem on 10/7/16.
 */
public interface View extends IsComponent {
	void addToSlot(Object slot, IsComponent content);
	void removeFromSlot(Object slot, IsComponent content);
	void setInSlot(Object slot, IsComponent content);
	void bindPresenter(Presenter presenter);
}
