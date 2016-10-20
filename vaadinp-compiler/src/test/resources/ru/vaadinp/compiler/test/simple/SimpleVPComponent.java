package ru.vaadinp.compiler.test.simple;

import dagger.Lazy;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Binds;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

@Singleton
public class SimpleVPComponent extends VPComponent<SimplePresenter, SimpleView> {

	@Module
	public interface Declarations {
		@Binds
		VPComponent<? extends PresenterComponent<Simple.View>, ?> vpComponent(SimpleVPComponent vpComponent);

		@Binds
		Simple.View view(SimpleView view);

		@Binds
		Simple.Presenter presenter(SimplePresenter presenter);
	}

	@Inject
	public SimpleVPComponent(Lazy<SimplePresenter> lazyPresenterComponent, Lazy<SimpleView> lazyView) {
		super(lazyPresenterComponent, lazyView);
	}
}
