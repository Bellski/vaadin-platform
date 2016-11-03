package ru.vaadinp.test.vp;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.test.MockUtils;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.View;
import ru.vaadinp.vp.ViewImpl;

/**
 * Created by oem on 10/11/16.
 */
@RunWith(JUnit4.class)
public class BaseNestedPresenterTest {

	private final RootMVP rootVPComponent = MockUtils.mockRootVP();

	@Test
	@Ignore
	public void forceRevealPresenter() {
		final BaseNestedPresenter<View> baseNestedPresenter = new BaseNestedPresenter<>(new ViewImpl<>(), RootPresenter.ROOT_SLOT);

		baseNestedPresenter.forceReveal();

//		assertTrue(nestedPresenter.isVisible());
	}
}
