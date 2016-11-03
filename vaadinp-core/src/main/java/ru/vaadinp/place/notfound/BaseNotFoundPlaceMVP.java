package ru.vaadinp.place.notfound;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.place.Place;
import ru.vaadinp.vp.api.NestedMVP;
import ru.vaadinp.vp.api.PlaceMVP;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/7/16.
 */
@Singleton
public class BaseNotFoundPlaceMVP extends NestedMVPImpl<BaseNotFoundPlacePresenter> {
	@Module
	public interface Declarations {

		@Binds
		NestedMVP<? extends BaseNestedPresenter<BaseNotFoundPlace.View>> mvp(BaseNotFoundPlaceMVP mvp);

		@Binds
		BaseNotFoundPlace.Presenter presenter(BaseNotFoundPlacePresenter presenter);

		@Binds
		BaseNotFoundPlace.View view(BaseNotFoundPlaceView view);

		@Binds
		@IntoMap
		@PlacesMap
		@StringKey(BaseNotFoundToken.ENCODED_VAADINP_NOUTFOUND)
		PlaceMVP<?> place(BaseNotFoundPlaceMVP mvp);
	}


	@Inject
	public BaseNotFoundPlaceMVP(Lazy<BaseNotFoundPlacePresenter> lazyPresenter,
								Lazy<BaseNotFoundPlaceView> lazyView,
								RootMVP rootMVP,
								RootMVP parent) {
		
		super (
			null,
			lazyView,
			lazyPresenter,
			rootMVP,
			parent,
			new Place(BaseNotFoundToken.VAADINP_NOUTFOUND_NAME_TOKEN)
		);
	}
}
