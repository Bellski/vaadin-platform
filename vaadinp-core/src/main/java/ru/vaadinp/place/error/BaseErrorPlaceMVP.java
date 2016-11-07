package ru.vaadinp.place.error;

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
 * Created by oem on 10/10/16.
 */
@Singleton
public class BaseErrorPlaceMVP extends NestedMVPImpl<BaseErrorPlacePresenter> {

	@Module
	public interface Declarations  {

		@Binds
		NestedMVP<? extends BaseNestedPresenter<BaseErrorPlace.View >> mvp(BaseErrorPlaceMVP mvp);

		@Binds
		BaseErrorPlace.Presenter presenter(BaseErrorPlacePresenter presenter);

		@Binds
		BaseErrorPlace.View view(BaseErrorPlaceView view);

		@Binds
		@IntoMap
		@PlacesMap
		@StringKey(BaseErrorPlaceToken.ENCODED_VAADINP_ERROR)
		PlaceMVP<?> place(BaseErrorPlaceMVP mvp);
	}


	@Inject
	public BaseErrorPlaceMVP(Lazy<BaseErrorPlacePresenter> lazyPresenter,
							 Lazy<BaseErrorPlaceView> lazyView,
							 RootMVP rootMVP,
							 RootMVP parent) {

		super (
			null,
			lazyView,
			lazyPresenter,
			rootMVP,
			parent, new Place(BaseErrorPlaceToken.VAADINP_ERROR_NAME_TOKEN)
		);
	}
}
