package ru.vaadinp.test.slot;

import dagger.internal.DoubleCheck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.test.NestedMVPBuilder;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.api.NestedMVP;

import static ru.vaadinp.test.MockUtils.mockRootVP;

/**
 * Created by oem on 10/11/16.
 */
@RunWith(JUnit4.class)
public class SlotTest {

	private final RootMVP rootVPComponent = mockRootVP();

	private final NestedMVP<? extends BaseNestedPresenter<?>> aVPComponent = new NestedMVPBuilder(rootVPComponent, rootVPComponent, RootPresenter.ROOT_SLOT)
		.withPresenter(DoubleCheck.lazy(() -> new PresenterAMockBase(RootPresenter.ROOT_SLOT)))
		.build();

	private final NestedMVP<? extends BaseNestedPresenter<?>> bVPComponent = new NestedMVPBuilder(aVPComponent, rootVPComponent, PresenterAMockBase.MAIN_SLOT)
		.withPresenter(DoubleCheck.lazy(() -> new PresenterBMockBase(PresenterAMockBase.MAIN_SLOT)))
		.build();

	@Test
	public void testNestedSlotReveal() {
		final PresenterBMockBase presenterBMock = (PresenterBMockBase) bVPComponent.getPresenter();

		Assert.assertFalse(presenterBMock.isVisible());
		Assert.assertFalse(aVPComponent.getPresenter().isVisible());

		presenterBMock.forceReveal();

		Assert.assertTrue(presenterBMock.isVisible());
		Assert.assertTrue(aVPComponent.getPresenter().isVisible());
	}
}
