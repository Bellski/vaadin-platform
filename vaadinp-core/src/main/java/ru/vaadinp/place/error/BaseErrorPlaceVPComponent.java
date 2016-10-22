package ru.vaadinp.place.error;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.slot.SlotRevealBus;
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
public class BaseErrorPlaceVPComponent extends PlaceVPComponent<BaseErrorPresenter, BaseErrorView> {

	@Module
	public interface Declarations  {

		@Binds
		VPComponent<? extends PresenterComponent<BaseError.View>, ?> vpComponent(BaseErrorPlaceVPComponent vpComponent);

		@Binds
		BaseError.Presenter presenter(BaseErrorPresenter presenter);

		@Binds
		BaseError.View view(BaseErrorView view);

		@Binds
		@IntoMap
		@PlacesMap
		@StringKey(NAME_TOKEN)
		PlaceVPComponent<?, ?> place(BaseErrorPlaceVPComponent vpComponent);
	}

	public static final String NAME_TOKEN = "!/vaadinp-error";


	@Inject
	public BaseErrorPlaceVPComponent(Lazy<BaseErrorPresenter> lazyPresenterComponent,
									 Lazy<BaseErrorView> lazyView,
									 RootVPComponent parent) {

		super(NAME_TOKEN, lazyPresenterComponent, lazyView, parent);
	}
}
