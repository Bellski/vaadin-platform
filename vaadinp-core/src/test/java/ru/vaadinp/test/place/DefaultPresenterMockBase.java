package ru.vaadinp.test.place;

import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.api.Presenter;
import ru.vaadinp.vp.View;
import ru.vaadinp.vp.ViewImpl;

/**
 * Created by oem on 10/10/16.
 */
public class DefaultPresenterMockBase extends BaseNestedPresenter<View> implements Presenter {
	public final static String NAME_TOKEN = "!/dummyDefaultPlace";

	public DefaultPresenterMockBase() {
		super(new ViewImpl<>(), RootPresenter.ROOT_SLOT);
	}
}
