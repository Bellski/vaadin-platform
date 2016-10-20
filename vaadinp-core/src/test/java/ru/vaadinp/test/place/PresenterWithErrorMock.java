package ru.vaadinp.test.place;

import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.Presenter;
import ru.vaadinp.vp.View;
import ru.vaadinp.vp.ViewImpl;

/**
 * Created by oem on 10/11/16.
 */
public class PresenterWithErrorMock extends NestedPresenter<View> implements Presenter {
	public static String NAME_TOKEN = "!/dummyPresenterWithError";

	public PresenterWithErrorMock() {
		super(new ViewImpl<>(), RootPresenter.ROOT_SLOT);

		throw new RuntimeException("Шеф все пропало.");
	}
}
