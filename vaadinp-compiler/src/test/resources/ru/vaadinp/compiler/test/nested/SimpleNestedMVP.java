package ru.vaadinp.compiler.test.nested;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.api.NestedMVP;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.vaadinp.slot.root.RootMVP;

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
