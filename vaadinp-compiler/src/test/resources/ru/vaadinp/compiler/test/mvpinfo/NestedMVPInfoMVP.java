package ru.vaadinp.compiler.test.mvpinfo;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.MVPInfo;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.api.NestedMVP;


@Singleton
public class NestedMVPInfoMVP extends NestedMVPImpl<NestedMVPInfoPresenter> {

	@Inject
	public NestedMVPInfoMVP(Lazy<NestedMVPInfoView> view,
                            Lazy<NestedMVPInfoPresenter> presenter,
                            RootMVP rootMVP,
                            RootMVP parent) {

		super(view, presenter, rootMVP, new MVPInfo("title", "!/historyToken"), parent);
	}

	@Module
	public interface Declarations {
		@Binds
		NestedMVP<? extends BaseNestedPresenter<NestedMVPInfo.View>> mvp(NestedMVPInfoMVP mvp);

		@Binds
		NestedMVPInfo.View view(NestedMVPInfoView view);

		@Binds
		NestedMVPInfo.Presenter presenter(NestedMVPInfoPresenter presenter);
	}
}
