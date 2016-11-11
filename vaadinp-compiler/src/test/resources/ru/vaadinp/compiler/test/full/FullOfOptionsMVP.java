package ru.vaadinp.compiler.test.full;

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
import ru.vaadinp.vp.MVPInfo;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.api.NestedMVP;
import ru.vaadinp.vp.api.PlaceMVP;

@Singleton
public class FullOfOptionsMVP extends NestedMVPImpl<FullOfOptionsPresenter> {

	@Inject
	public FullOfOptionsMVP(Lazy<FullOfOptionsView> view,
                            Lazy<FullOfOptionsPresenter> presenter,
                            RootMVP rootMVP,
                            RootMVP parent) {

		super(
			view,
			presenter,
			rootMVP,
			new MVPInfo("caption", "title", "!/fullOfOptions", 0),
			parent,
			new Place(FullOfOptionsTokenSet.FULLOFOPTIONS_TOKEN, FullOfOptionsTokenSet.FULLOFOPTIONS_SOMEPARAMETER_TOKEN)
		);
	}

	@Module
	public interface Declarations {
		@Binds
		NestedMVP<? extends BaseNestedPresenter<FullOfOptions.View>> mvp(FullOfOptionsMVP mvp);

		@Binds
		FullOfOptions.View view(FullOfOptionsView view);

		@Binds
		FullOfOptions.Presenter presenter(FullOfOptionsPresenter presenter);

		@IntoMap
		@PlacesMap
		@Binds
		@StringKey(FullOfOptionsTokenSet.ENCODED_FULLOFOPTIONS)
		PlaceMVP<?> fullOfOptions(FullOfOptionsMVP mvp);

		@IntoMap
		@PlacesMap
		@Binds
		@StringKey(FullOfOptionsTokenSet.ENCODED_FULLOFOPTIONS_SOMEPARAMETER)
		PlaceMVP<?> fullOfOptions_someParameter(FullOfOptionsMVP mvp);
	}
}
