package ru.vaadinp.compiler.test.nestedWithNestedSlot;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.api.NestedMVP;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.MVPInfo;

import ru.vaadinp.slot.root.RootMVP;

@Singleton
public class WithNestedSlotMVP extends NestedMVPImpl<WithNestedSlotPresenter> {

	@Module
	public interface Declarations {
		@Binds
		NestedMVP<? extends BaseNestedPresenter<WithNestedSlot.View>> mvp(WithNestedSlotMVP mvp);

		@Binds
		WithNestedSlot.View view(WithNestedSlotView view);

		@Binds
		WithNestedSlot.Presenter presenter(WithNestedSlotPresenter presenter);


		@Provides
		@Singleton
		@RevealIn(WithNestedSlotPresenter.class)
		static NestedSlot nestedSlot() {
			return WithNestedSlotPresenter.MAIN_SLOT;
		}

	}

	@Inject
	public WithNestedSlotMVP(Lazy<WithNestedSlotView> lazyView,
							 Lazy<WithNestedSlotPresenter> lazyPresenter,
							 RootMVP rootMVP,
							 RootMVP parent) {

		super(
			null,
			lazyView,
			lazyPresenter,
			new MVPInfo(null, "WithNestedSlot", null, -1),
			rootMVP,
			parent,
			null
		);
	}
}
