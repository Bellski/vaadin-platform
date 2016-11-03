package ru.vaadinp.compiler.test.place;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.api.NestedMVP;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.place.Place;
import ru.vaadinp.vp.api.PlaceMVP;

@Singleton
public class SimplePlaceMVP extends NestedMVPImpl<SimplePlacePresenter> {

	@Module
	public interface Declarations {
		@Binds
		NestedMVP<? extends BaseNestedPresenter<SimplePlace.View>> mvp(SimplePlaceMVP mvp);

		@Binds
		SimplePlace.View view(SimplePlaceView view);

		@Binds
		SimplePlace.Presenter presenter(SimplePlacePresenter presenter);

		@IntoMap
		@PlacesMap
		@Binds
		@StringKey(SimplePlaceToken.ENCODED_SIMPLE)
		PlaceMVP<?> place(SimplePlaceMVP mvp);
	}

	@Inject
	public SimplePlaceMVP(Lazy<SimplePlaceView> lazyView,
						  Lazy<SimplePlacePresenter> lazyPresenter,
						  RootMVP rootMVP,
						  RootMVP parent) {

		super (
			null,
			lazyView,
			lazyPresenter,
			null,
			rootMVP,
			parent,
			new Place(SimplePlaceToken.SIMPLE_TOKEN)
		);
	}
}
