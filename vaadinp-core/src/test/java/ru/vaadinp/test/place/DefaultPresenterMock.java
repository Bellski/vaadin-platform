package ru.vaadinp.test.place;

import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.Presenter;
import ru.vaadinp.vp.View;
import ru.vaadinp.vp.ViewImpl;

/**
 * Created by oem on 10/10/16.
 */
public class DefaultPresenterMock extends NestedPresenter<View> implements Presenter {
	public final static String NAME_TOKEN = "!/dummyDefaultPlace";

	public DefaultPresenterMock() {
		super(new ViewImpl<>(), RootPresenter.ROOT_SLOT);
	}
}
