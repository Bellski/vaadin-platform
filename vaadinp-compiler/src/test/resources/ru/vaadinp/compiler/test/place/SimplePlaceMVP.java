package ru.vaadinp.compiler.test.place;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.place.Place;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.api.NestedMVP;
import ru.vaadinp.vp.api.PlaceMVP;

@Singleton
public class SimplePlaceMVP extends NestedMVPImpl<SimplePlacePresenter> {

	@Inject
	public SimplePlaceMVP(Lazy<SimplePlaceView> view,
						  Lazy<SimplePlacePresenter> presenter,
						  RootMVP rootMVP,
						  RootMVP parent) {

		super(
			view,
			presenter,
			rootMVP,
			parent,
			new Place(SimplePlaceTokenSet.SIMPLEPLACE_TOKEN)
		);
	}

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
		PlaceMVP<?> simplePlace(SimplePlaceMVP mvp);
	}
}
