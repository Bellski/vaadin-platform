package ru.vaadinp.slot.root;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import ru.vaadinp.annotations.dagger.IntoSlotMap;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.NestedVPComponent;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.AbstractMap;
import java.util.Map;

/**
 * Created by oem on 10/14/16.
 */
@Singleton
public class RootVPComponent extends NestedVPComponent<RootPresenter, RootView> {

	@Module
	public interface Declarations  {

		@Binds
		VPComponent<? extends PresenterComponent<RootView>, ?> vpComponent(RootVPComponent vpComponent);

		@Provides
		@Singleton
		@IntoSlotMap
		@IntoSet
		static Map.Entry<NestedSlot, NestedVPComponent<?, ?>> bindNestedSlot(RootVPComponent vpComponent) {
			return new AbstractMap.SimpleImmutableEntry<>(RootPresenter.ROOT_SLOT, vpComponent);
		}

		@Provides
		@Singleton
		@RevealIn(RootPresenter.class)
		static NestedSlot nestedSlot() {
			return RootPresenter.ROOT_SLOT;
		}

	}

	@Inject
	public RootVPComponent(Lazy<RootPresenter> lazyPresenterComponent, Lazy<RootView> lazyView) {
		super(lazyPresenterComponent, lazyView);
	}

}
