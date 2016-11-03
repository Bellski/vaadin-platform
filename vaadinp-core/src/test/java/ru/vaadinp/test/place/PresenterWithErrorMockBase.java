package ru.vaadinp.test.place;

import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.api.Presenter;
import ru.vaadinp.vp.View;
import ru.vaadinp.vp.ViewImpl;

/**
 * Created by oem on 10/11/16.
 */
public class PresenterWithErrorMockBase extends BaseNestedPresenter<View> implements Presenter {
	public static String NAME_TOKEN = "!/dummyPresenterWithError";

	public PresenterWithErrorMockBase() {
		super(new ViewImpl<>(), RootPresenter.ROOT_SLOT);

		throw new RuntimeException("Шеф все пропало.");
	}
}
