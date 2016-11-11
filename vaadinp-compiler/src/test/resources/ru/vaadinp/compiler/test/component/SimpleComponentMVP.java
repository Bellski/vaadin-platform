package ru.vaadinp.compiler.test.component;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.MVPImpl;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.api.MVP;

@Singleton
public class SimpleComponentMVP extends MVPImpl<SimpleComponentPresenter> {
	@Inject
	public SimpleComponentMVP(Lazy<SimpleComponentView> view, Lazy<SimpleComponentPresenter> presenter, RootMVP rootMVP) {
		super(view, presenter, rootMVP);
	}

	@Module
	public interface Declarations {
		@Binds
		MVP<? extends PresenterComponent<SimpleComponent.View>> mvp(SimpleComponentMVP mvp);

		@Binds
		SimpleComponent.View view(SimpleComponentView view);

		@Binds
		SimpleComponent.Presenter presenter(SimpleComponentPresenter presenter);
	}
}
