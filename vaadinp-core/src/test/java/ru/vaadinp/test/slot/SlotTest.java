package ru.vaadinp.test.slot;

import dagger.internal.DoubleCheck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.vaadinp.slot.BaseSlotRevealBus;
import ru.vaadinp.slot.SlotRevealBus;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.slot.root.RootVPComponent;
import ru.vaadinp.test.MockUtils;
import ru.vaadinp.vp.NestedVPComponent;

import java.util.HashSet;

import static ru.vaadinp.test.MockUtils.mockRootVP;

/**
 * Created by oem on 10/11/16.
 */
@RunWith(JUnit4.class)
public class SlotTest {

	private final RootVPComponent rootVPComponent = mockRootVP();

	private final NestedVPComponent<?, ?> aVPComponent = MockUtils.mockNestedVP(
		DoubleCheck.lazy(PresenterAMock::new), rootVPComponent, RootPresenter.ROOT_SLOT
	);

	private final NestedVPComponent<?, ?> bVPComponent = MockUtils.mockNestedVP(
		DoubleCheck.lazy(PresenterBMock::new), aVPComponent, PresenterAMock.MAIN_SLOT
	);

	@Test
	public void testNestedSlotReveal() {
		final PresenterBMock presenterBMock = (PresenterBMock) bVPComponent.getPresenter();

		Assert.assertFalse(presenterBMock.isVisible());
		Assert.assertFalse(aVPComponent.getPresenter().isVisible());

		presenterBMock.forceReveal();

		Assert.assertTrue(presenterBMock.isVisible());
		Assert.assertTrue(aVPComponent.getPresenter().isVisible());
	}
}
