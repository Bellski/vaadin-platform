package ru.vaadinp.compiler2.test.full;

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
import ru.vaadinp.vp.MVPInfo;
import ru.vaadinp.vp.api.PlaceMVP;
import ru.vaadinp.vp.api.NestedMVP;
import ru.vaadinp.slot.root.RootMVP;

@Singleton
public class FullOfOptionsMVP extends NestedMVPImpl<FullOfOptionsPresenter> {

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
		PlaceMVP<?> fullofoptions_place(FullOfOptionsMVP mvp);

		@IntoMap
		@PlacesMap
		@Binds
		@StringKey(FullOfOptionsTokenSet.ENCODED_FULLOFOPTIONS__SOMEPARAMETER_)
		PlaceMVP<?> fullofoptions__someparameter__place(FullOfOptionsMVP mvp);
	}

	@Inject
	public FullOfOptionsMVP(Lazy<FullOfOptionsView> lazyView,
                            Lazy<FullOfOptionsPresenter> lazyPresenter,
                            RootMVP rootMVP,
                            RootMVP parent) {

		super(
			null,
			lazyView,
			lazyPresenter,
			new MVPInfo("caption", "title", "!/fullOfOptions", 0),
			rootMVP,
			parent,
			new Place(FullOfOptionsTokenSet.FULLOFOPTIONS_TOKEN, FullOfOptionsTokenSet.FULLOFOPTIONS__SOMEPARAMETER__TOKEN)
		);
	}
}
