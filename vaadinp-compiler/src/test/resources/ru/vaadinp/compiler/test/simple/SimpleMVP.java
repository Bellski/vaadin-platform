package ru.vaadinp.compiler.test.simple;

import dagger.Lazy;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Binds;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.MVPImpl;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.api.MVP;

@Singleton
public class SimpleMVP extends MVPImpl<SimplePresenter> {

	@Module
	public interface Declarations {
		@Binds
		MVP<? extends PresenterComponent<Simple.View>> mvp(SimpleMVP mvp);

		@Binds
		Simple.View view(SimpleView view);

		@Binds
		Simple.Presenter presenter(SimplePresenter presenter);
	}

	@Inject
	public SimpleMVP(Lazy<SimpleView> lazyView, Lazy<SimplePresenter> lazyPresenter, RootMVP rootMVP) {
		super(lazyView, lazyPresenter, rootMVP);
	}
}
