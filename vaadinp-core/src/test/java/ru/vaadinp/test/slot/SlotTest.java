package ru.vaadinp.test.slot;

import dagger.internal.DoubleCheck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.vaadinp.slot.BaseSlotRevealBus;
import ru.vaadinp.slot.SlotRevealBus;
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
	private final SlotRevealBus slotRevealBus = new BaseSlotRevealBus(new HashSet<>());

	private final RootVPComponent rootVPComponent = mockRootVP(slotRevealBus);

	private final NestedVPComponent<?, ?> aVPComponent = MockUtils.mockNestedVP(
		DoubleCheck.lazy(PresenterAMock::new), slotRevealBus, PresenterAMock.MAIN_SLOT, true
	);

	private final NestedVPComponent<?, ?> bVPComponent = MockUtils.mockNestedVP(
		DoubleCheck.lazy(PresenterBMock::new), slotRevealBus, PresenterAMock.MAIN_SLOT, false
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
