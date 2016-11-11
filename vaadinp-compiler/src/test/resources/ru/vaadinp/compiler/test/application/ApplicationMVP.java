package ru.vaadinp.compiler.test.application;

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
public class ApplicationMVP extends NestedMVPImpl<ApplicationPresenter> {

	@Inject
	public ApplicationMVP(Lazy<ApplicationView> view,
						  Lazy<ApplicationPresenter> presenter,
						  RootMVP rootMVP,
						  RootMVP parent) {

		super(
			view,
			presenter,
			rootMVP,
			parent
		);
	}

	@Module
	public interface Declarations {
		@Binds
		NestedMVP<? extends BaseNestedPresenter<Application.View>> mvp(ApplicationMVP mvp);

		@Binds
		Application.View view(ApplicationView view);

		@Binds
		Application.Presenter presenter(ApplicationPresenter presenter);

		@Provides
		@Singleton
		@RevealIn(ApplicationPresenter.class)
		static NestedSlot nestedSlot() {
			return ApplicationPresenter.MAIN_SLOT;
		}
	}
}
