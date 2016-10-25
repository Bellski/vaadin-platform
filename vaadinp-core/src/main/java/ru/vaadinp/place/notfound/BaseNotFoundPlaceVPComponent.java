package ru.vaadinp.place.notfound;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.slot.root.RootVPComponent;
import ru.vaadinp.vp.PlaceVPComponent;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/7/16.
 */
@Singleton
public class BaseNotFoundPlaceVPComponent extends PlaceVPComponent<BaseNotFoundPlacePresenter, BaseNotFoundPlaceView> {
	@Module
	public interface Declarations {

		@Binds
		VPComponent<? extends PresenterComponent<BaseNotFoundPlace.View>, ?> vpComponent(BaseNotFoundPlaceVPComponent vpComponent);

		@Binds
		BaseNotFoundPlace.Presenter baseNotFoundPresenter(BaseNotFoundPlacePresenter presenter);

		@Binds
		BaseNotFoundPlace.View baseNotFoundView(BaseNotFoundPlaceView view);

		@Binds
		@IntoMap
		@PlacesMap
		@StringKey(BaseNotFoundPlacePresenter.NAME_TOKEN)
		PlaceVPComponent<?, ?> baseNotFoundPlace(BaseNotFoundPlaceVPComponent vpComponent);
	}


	@Inject
	public BaseNotFoundPlaceVPComponent(Lazy<BaseNotFoundPlacePresenter> lazyPresenterComponent,
										Lazy<BaseNotFoundPlaceView> lazyView,
										RootVPComponent parent) {
		
		super(BaseNotFoundPlacePresenter.NAME_TOKEN, lazyPresenterComponent, lazyView, parent);
	}
}
