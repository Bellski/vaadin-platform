package ru.vaadinp.slot.root;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.api.NestedMVP;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/14/16.
 */
@Singleton
public class RootMVP extends NestedMVPImpl<RootPresenter> {

    @Module
    public interface Declarations {
        @Binds
        NestedMVP<? extends BaseNestedPresenter<Root.View>> mvp(RootMVP vpComponent);

        @Binds
        Root.View view(RootView view);

        @Binds
        Root.Presenter presenter(RootPresenter presenter);

        @Provides
        @Singleton
        @RevealIn(RootPresenter.class)
        static NestedSlot nestedSlot() {
            return RootPresenter.ROOT_SLOT;
        }
    }

    @Inject
    public RootMVP(Lazy<RootPresenter> lazyPresenter, Lazy<RootView> lazyView) {
        super(lazyView, lazyPresenter, null, null);
    }

}
