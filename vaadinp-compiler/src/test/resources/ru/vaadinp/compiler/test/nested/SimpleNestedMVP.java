package ru.vaadinp.compiler.test.nested;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.api.NestedMVP;

@Singleton
public class SimpleNestedMVP extends NestedMVPImpl<SimpleNestedPresenter> {

	@Inject
	public SimpleNestedMVP(Lazy<SimpleNestedView> view,
						   Lazy<SimpleNestedPresenter> presenter,
						   RootMVP rootMVP,
						   RootMVP parent) {

		super(view, presenter, rootMVP, parent);
	}

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
}
