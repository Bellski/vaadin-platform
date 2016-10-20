package ru.vaadinp.compiler.test.nestedWithNestedSlot;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.slot.SlotRevealBus;
import ru.vaadinp.vp.NestedVPComponent;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import ru.vaadinp.annotations.dagger.IntoSlotMap;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import java.util.AbstractMap;
import java.util.Map;

@Singleton
public class WithNestedSlotVPComponent extends NestedVPComponent<WithNestedSlotPresenter, WithNestedSlotView> {

	@Module
	public interface Declarations {
		@Binds
		VPComponent<? extends PresenterComponent<WithNestedSlot.View>, ?> vpComponent(WithNestedSlotVPComponent vpComponent);

		@Binds
		WithNestedSlot.View view(WithNestedSlotView view);

		@Binds
		WithNestedSlot.Presenter presenter(WithNestedSlotPresenter presenter);


		@Provides
		@Singleton
		@IntoSlotMap
		@IntoSet
		static Map.Entry<NestedSlot, NestedVPComponent<?, ?>> bindNestedSlot(WithNestedSlotVPComponent vpComponent) {
			return new AbstractMap.SimpleImmutableEntry<>(WithNestedSlotPresenter.MAIN_SLOT, vpComponent);
		}

		@Provides
		@Singleton
		@RevealIn(WithNestedSlotPresenter.class)
		static NestedSlot nestedSlot() {
			return WithNestedSlotPresenter.MAIN_SLOT;
		}

	}

	@Inject
	public WithNestedSlotVPComponent(Lazy<WithNestedSlotPresenter> lazyPresenterComponent, Lazy<WithNestedSlotView> lazyView, SlotRevealBus slotRevealBus) {
		super(lazyPresenterComponent, lazyView, slotRevealBus);
	}
}
