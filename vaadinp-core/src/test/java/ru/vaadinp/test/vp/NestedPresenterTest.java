package ru.vaadinp.test.vp;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.vaadinp.slot.BaseSlotRevealBus;
import ru.vaadinp.slot.SlotRevealBus;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.slot.root.RootVPComponent;
import ru.vaadinp.test.MockUtils;
import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.View;
import ru.vaadinp.vp.ViewImpl;

import java.util.HashSet;

/**
 * Created by oem on 10/11/16.
 */
@RunWith(JUnit4.class)
public class NestedPresenterTest {
	private final SlotRevealBus slotRevealBus = new BaseSlotRevealBus(new HashSet<>());

	private final RootVPComponent rootVPComponent = MockUtils.mockRootVP(slotRevealBus);

	@Test
	@Ignore
	public void forceRevealPresenter() {
		final NestedPresenter<View> nestedPresenter = new NestedPresenter<>(new ViewImpl<>(), RootPresenter.ROOT_SLOT);

		nestedPresenter.forceReveal();

//		assertTrue(nestedPresenter.isVisible());
	}
}
