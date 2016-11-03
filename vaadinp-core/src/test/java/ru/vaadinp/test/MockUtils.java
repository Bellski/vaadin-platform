package ru.vaadinp.test;

import dagger.Lazy;
import dagger.internal.DoubleCheck;
import ru.vaadinp.place.error.BaseErrorPlacePresenter;
import ru.vaadinp.place.error.BaseErrorPlaceMVP;
import ru.vaadinp.place.error.BaseErrorPlaceView;
import ru.vaadinp.place.notfound.BaseNotFoundPlacePresenter;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceMVP;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceView;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.slot.root.RootView;

/**
 * Created by oem on 10/14/16.
 */
public class MockUtils {

	public static RootMVP mockRootVP() {
		final Lazy<RootView> lazyView = DoubleCheck.lazy(() -> new RootView(new UIMock()));

		return new RootMVP(DoubleCheck.lazy(() -> new RootPresenter(lazyView.get())), lazyView){
			@Override
			public RootPresenter getPresenter() {
				final RootPresenter presenter = super.getPresenter();

				presenter.injectMVP(this);

				return presenter;
			}
		};
	}

	public static BaseNotFoundPlaceMVP mockBaseNotFoundVP(RootMVP rootVPComponent) {
		final Lazy<BaseNotFoundPlaceView> lazyView = DoubleCheck.lazy(BaseNotFoundPlaceView::new);

		return new BaseNotFoundPlaceMVP(DoubleCheck.lazy(() -> new BaseNotFoundPlacePresenter(lazyView.get(), RootPresenter.ROOT_SLOT)), lazyView, rootVPComponent, rootVPComponent) {
			@Override
			public BaseNotFoundPlacePresenter getPresenter() {
				BaseNotFoundPlacePresenter baseNotFoundPlacePresenter = super.getPresenter();

				baseNotFoundPlacePresenter.injectMVP(this);

				return baseNotFoundPlacePresenter;
			}
		};
	}

	public static BaseErrorPlaceMVP mockBaseBaseErrorPlaceVP(RootMVP rootVPComponent) {
		final Lazy<BaseErrorPlaceView> lazyView = DoubleCheck.lazy(BaseErrorPlaceView::new);

		return new BaseErrorPlaceMVP(DoubleCheck.lazy(() -> new BaseErrorPlacePresenter(lazyView.get(), RootPresenter.ROOT_SLOT)), lazyView, rootVPComponent, rootVPComponent) {
			@Override
			public BaseErrorPlacePresenter getPresenter() {
				BaseErrorPlacePresenter baseErrorPlacePresenter = super.getPresenter();

				baseErrorPlacePresenter.injectMVP(this);

				return baseErrorPlacePresenter;
			}
		};
	}

}
