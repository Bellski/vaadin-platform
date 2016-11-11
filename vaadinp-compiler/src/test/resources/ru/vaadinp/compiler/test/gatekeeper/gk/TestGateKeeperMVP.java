package ru.vaadinp.compiler.test.gatekeeper.gk;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.compiler.test.gatekeeper.ApplicationMVP;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.GateKeeperMVPImpl;
import ru.vaadinp.vp.api.NestedMVP;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TestGateKeeperMVP extends GateKeeperMVPImpl<TestGateKeeperPresenter> {


    @Module
    public interface Declarations {
        @Binds
        NestedMVP<? extends BaseNestedPresenter<TestGateKeeper.View>> mvp(TestGateKeeperMVP mvp);

        @Binds
        TestGateKeeper.View view(TestGateKeeperView view);

        @Binds
        TestGateKeeper.Presenter presenter(TestGateKeeperPresenter presenter);
    }


    @Inject
    public TestGateKeeperMVP(Lazy<TestGateKeeperView> lazyView,
                             Lazy<TestGateKeeperPresenter> lazyPresenter,
                             RootMVP rootMVP,
                             ApplicationMVP parent) {
        super(lazyView, lazyPresenter, null, rootMVP, parent);
    }

}