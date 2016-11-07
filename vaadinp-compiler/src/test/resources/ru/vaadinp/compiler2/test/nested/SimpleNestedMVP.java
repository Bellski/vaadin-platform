package ru.vaadinp.compiler2.test.nested;

import javax.inject.Inject;
import dagger.Binds;
import dagger.Module;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.annotations.dagger.RevealIn;
import javax.inject.Singleton;
import ru.vaadinp.vp.api.NestedMVP;
import dagger.Lazy;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.slot.NestedSlot;
import dagger.Provides;

@Singleton
public class SimpleNestedMVP extends NestedMVPImpl<SimpleNestedPresenter> {

	@Module
	public interface Declarations {
		@Binds
		NestedMVP<? extends BaseNestedPresenter<SimpleNested.View>> mvp(SimpleNestedMVP mvp);

		@Binds
		SimpleNested.View view(SimpleNestedView view);

		@Binds
		SimpleNested.Presenter presenter(SimpleNestedPresenter presenter);

		@Provides
		@Singleton
		@RevealIn(SimpleNestedPresenter.class)
		static NestedSlot nestedSlot() {
			return SimpleNestedPresenter.MAIN_SLOT;
		}
	}

	@Inject
	public SimpleNestedMVP(Lazy<SimpleNestedView> lazyView,
						   Lazy<SimpleNestedPresenter> lazyPresenter,
						   RootMVP rootMVP,
						   RootMVP parent) {

		super(
			null,
			lazyView,
			lazyPresenter,
			null,
			rootMVP,
			parent,
			null
		);
	}
}
