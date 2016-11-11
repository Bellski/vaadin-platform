package ru.vaadinp.compiler.test.application.home;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.compiler.test.application.ApplicationMVP;
import ru.vaadinp.place.Place;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.api.NestedMVP;
import ru.vaadinp.vp.api.PlaceMVP;

@Singleton
public class HomeMVP extends NestedMVPImpl<HomePresenter> {



	@Inject
	public HomeMVP(Lazy<HomeView> view,
				   Lazy<HomePresenter> presenter,
				   RootMVP rootMVP,
				   ApplicationMVP parent) {

		super(
			view,
			presenter,
			rootMVP,
			parent,
			new Place(HomeTokenSet.HOME_TOKEN)
		);
	}

	@Module
	public interface Declarations {
		@Binds
		NestedMVP<? extends BaseNestedPresenter<Home.View>> mvp(HomeMVP mvp);

		@Binds
		Home.View view(HomeView view);

		@Binds
		Home.Presenter presenter(HomePresenter presenter);

		@IntoMap
		@PlacesMap
		@Binds
		@StringKey(HomeTokenSet.ENCODED_HOME)
		PlaceMVP<?> home(HomeMVP mvp);
	}
}
