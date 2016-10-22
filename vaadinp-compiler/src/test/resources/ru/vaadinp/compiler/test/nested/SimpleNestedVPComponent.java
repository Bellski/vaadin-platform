package ru.vaadinp.compiler.test.nested;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.vp.NestedVPComponent;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.VPComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.vaadinp.slot.root.RootVPComponent;

@Singleton
public class SimpleNestedVPComponent extends NestedVPComponent<SimpleNestedPresenter, SimpleNestedView> {

	@Module
	public interface Declarations {
		@Binds
		VPComponent<? extends PresenterComponent<SimpleNested.View>, ?> vpComponent(SimpleNestedVPComponent vpComponent);

		@Binds
		SimpleNested.View view(SimpleNestedView view);

		@Binds
		SimpleNested.Presenter presenter(SimpleNestedPresenter presenter);
	}

	@Inject
	public SimpleNestedVPComponent(Lazy<SimpleNestedPresenter> lazyPresenterComponent,
								   Lazy<SimpleNestedView> lazyView,
								   RootVPComponent parent) {

		super(lazyPresenterComponent, lazyView, parent);
	}
}
