package ru.vaadinp.vp;

import dagger.Lazy;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.api.MVP;
import ru.vaadinp.vp.api.Presenter;

/**
 * Created by oem on 10/12/16.
 */
public class MVPImpl<P extends PresenterComponent<?>>  implements MVP<P> {
	private final Lazy<? extends ViewImpl<?>> lazyView;
	private final Lazy<P> lazyPresenter;
	private final MVPInfo info;
	private RootMVP rootMVP;


	public MVPImpl(Lazy<? extends ViewImpl<?>> lazyView,
				   Lazy<P> lazyPresenter,
				   RootMVP rootMVP) {
		this(lazyView, lazyPresenter, rootMVP, null);
	}

	public MVPImpl(Lazy<? extends ViewImpl<?>> lazyView,
				   Lazy<P> lazyPresenter,
				   RootMVP rootMVP,
				   MVPInfo info) {

		this.info = info;
		this.lazyPresenter = lazyPresenter;
		this.lazyView = lazyView;
		this.rootMVP = rootMVP;
	}

	@Override
	public Object getModel() {
		return null;
	}

	@Override
	public View getView() {
		return lazyView.get();
	}

	@Override
	public P getPresenter() {
		P presenter = lazyPresenter.get();

		if (presenter.getMVP() == null) {
			presenter.injectMVP(this);
		}

		return presenter;
	}

	@Override
	public MVPInfo getInfo() {
		return info;
	}

	@Override
	public RootMVP getRootMVP() {
		return rootMVP;
	}
}
