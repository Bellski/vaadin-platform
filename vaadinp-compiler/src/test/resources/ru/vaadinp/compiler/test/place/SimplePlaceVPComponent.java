package ru.vaadinp.compiler.test.place;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.slot.SlotRevealBus;
import ru.vaadinp.vp.PlaceVPComponent;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SimplePlaceVPComponent extends PlaceVPComponent<SimplePlacePresenter, SimplePlaceView> {

	@Module
	public interface Declarations {
		@Binds
		VPComponent<? extends PresenterComponent<SimplePlace.View>, ?> vpComponent(SimplePlaceVPComponent vpComponent);

		@Binds
		SimplePlace.View view(SimplePlaceView view);

		@Binds
		SimplePlace.Presenter presenter(SimplePlacePresenter presenter);

		@IntoMap
		@PlacesMap
		@Binds
		@StringKey(SimplePlacePresenter.NAME_TOKEN)
		PlaceVPComponent<?, ?> place(SimplePlaceVPComponent vpComponent);
	}

	@Inject
	public SimplePlaceVPComponent(Lazy<SimplePlacePresenter> lazyPresenterComponent, Lazy<SimplePlaceView> lazyView, SlotRevealBus slotRevealBus) {
		super(SimplePlacePresenter.NAME_TOKEN, lazyPresenterComponent, lazyView, slotRevealBus);
	}
}
