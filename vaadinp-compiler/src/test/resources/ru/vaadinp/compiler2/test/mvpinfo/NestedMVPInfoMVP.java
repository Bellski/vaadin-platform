package ru.vaadinp.compiler2.test.mvpinfo;

import javax.inject.Inject;
import dagger.Binds;
import dagger.Module;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.MVPInfo;
import ru.vaadinp.vp.NestedMVPImpl;
import javax.inject.Singleton;
import ru.vaadinp.vp.api.NestedMVP;
import dagger.Lazy;
import ru.vaadinp.slot.root.RootMVP;


@Singleton
public class NestedMVPInfoMVP extends NestedMVPImpl<NestedMVPInfoPresenter> {

	@Module
	public interface Declarations {
		@Binds
		NestedMVP<? extends BaseNestedPresenter<NestedMVPInfo.View>> mvp(NestedMVPInfoMVP mvp);

		@Binds
		NestedMVPInfo.View view(NestedMVPInfoView view);

		@Binds
		NestedMVPInfo.Presenter presenter(NestedMVPInfoPresenter presenter);
	}

	@Inject
	public NestedMVPInfoMVP(Lazy<NestedMVPInfoView> lazyView,
                            Lazy<NestedMVPInfoPresenter> lazyPresenter,
                            RootMVP rootMVP,
                            RootMVP parent) {

		super(
			null,
			lazyView,
			lazyPresenter,
			new MVPInfo("", "title", "!/historyToken", -1),
			rootMVP,
			parent,
			null
		);
	}
}
