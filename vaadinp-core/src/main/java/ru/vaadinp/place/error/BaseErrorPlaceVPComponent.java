package ru.vaadinp.place.error;

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
 * Created by oem on 10/10/16.
 */
@Singleton
public class BaseErrorPlaceVPComponent extends PlaceVPComponent<BaseErrorPlacePresenter, BaseErrorPlaceView> {

	@Module
	public interface Declarations  {

		@Binds
		VPComponent<? extends PresenterComponent<BaseErrorPlace.View>, ?> vpComponent(BaseErrorPlaceVPComponent vpComponent);

		@Binds
		BaseErrorPlace.Presenter presenter(BaseErrorPlacePresenter presenter);

		@Binds
		BaseErrorPlace.View view(BaseErrorPlaceView view);

		@Binds
		@IntoMap
		@PlacesMap
		@StringKey(BaseErrorPlacePresenter.NAME_TOKEN)
		PlaceVPComponent<?, ?> place(BaseErrorPlaceVPComponent vpComponent);
	}


	@Inject
	public BaseErrorPlaceVPComponent(Lazy<BaseErrorPlacePresenter> lazyPresenterComponent,
									 Lazy<BaseErrorPlaceView> lazyView,
									 RootVPComponent parent) {

		super(BaseErrorPlacePresenter.NAME_TOKEN, lazyPresenterComponent, lazyView, parent);
	}
}
