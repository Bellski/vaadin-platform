package ru.vaadinp.compiler2.test.place;

import dagger.Binds;
import ru.vaadinp.vp.NestedMVPImpl;
import javax.inject.Singleton;
import dagger.multibindings.IntoMap;
import dagger.Lazy;
import javax.inject.Inject;
import ru.vaadinp.place.Place;
import dagger.multibindings.StringKey;
import dagger.Module;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.vp.api.PlaceMVP;
import ru.vaadinp.vp.api.NestedMVP;
import ru.vaadinp.slot.root.RootMVP;

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
		@StringKey(SimplePlaceTokenSet.ENCODED_SIMPLEPLACE)
		PlaceMVP<?> simpleplace_place(SimplePlaceMVP mvp);
	}

	@Inject
	public SimplePlaceMVP(Lazy<SimplePlaceView> lazyView,
                          Lazy<SimplePlacePresenter> lazyPresenter,
                          RootMVP rootMVP,
                          RootMVP parent) {

		super(
			null,
			lazyView,
			lazyPresenter,
			null,
			rootMVP,
			parent,
			new Place(SimplePlaceTokenSet.SIMPLEPLACE_TOKEN)
		);
	}
}
