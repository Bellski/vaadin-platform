package ru.vaadinp.place.notfound;

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

/**
 * Created by oem on 10/7/16.
 */
@Singleton
public class BaseNotFoundPlaceVPComponent extends PlaceVPComponent<BaseNotFoundPresenter, BaseNotFoundView> {
	@Module
	public interface Declarations {

		@Binds
		VPComponent<? extends PresenterComponent<BaseNotFound.View>, ?> vpComponent(BaseNotFoundPlaceVPComponent vpComponent);

		@Binds
		BaseNotFound.Presenter baseNotFoundPresenter(BaseNotFoundPresenter presenter);

		@Binds
		BaseNotFound.View baseNotFoundView(BaseNotFoundView view);

		@Binds
		@IntoMap
		@PlacesMap
		@StringKey(NAME_TOKEN)
		PlaceVPComponent<?, ?> baseNotFoundPlace(BaseNotFoundPlaceVPComponent vpComponent);
	}

	public static final String NAME_TOKEN = "!/vaadinp-notfound";


	@Inject
	public BaseNotFoundPlaceVPComponent(Lazy<BaseNotFoundPresenter> lazyPresenterComponent,
										Lazy<BaseNotFoundView> lazyView,
										SlotRevealBus slotRevealBus) {
		
		super(NAME_TOKEN, lazyPresenterComponent, lazyView, slotRevealBus);
	}
}
