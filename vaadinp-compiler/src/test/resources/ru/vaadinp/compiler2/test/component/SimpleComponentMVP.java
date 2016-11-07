package ru.vaadinp.compiler2.test.component;

import javax.inject.Inject;
import dagger.Binds;
import dagger.Module;
import ru.vaadinp.vp.MVPImpl;
import javax.inject.Singleton;
import ru.vaadinp.vp.PresenterComponent;
import dagger.Lazy;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.api.MVP;

@Singleton
public class SimpleComponentMVP extends MVPImpl<SimpleComponentPresenter> {

	@Module
	public interface Declarations {
		@Binds
		MVP<? extends PresenterComponent<SimpleComponent.View>> mvp(SimpleComponentMVP mvp);

		@Binds
		SimpleComponent.View view(SimpleComponentView view);

		@Binds
		SimpleComponent.Presenter presenter(SimpleComponentPresenter presenter);
	}

	@Inject
	public SimpleComponentMVP(Lazy<SimpleComponentView> lazyView, Lazy<SimpleComponentPresenter> lazyPresenter, RootMVP rootMVP) {
		super(lazyView, lazyPresenter, rootMVP);
	}
}
