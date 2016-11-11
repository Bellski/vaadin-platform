package ru.vaadinp.test;

import dagger.Lazy;
import dagger.internal.DoubleCheck;
import ru.vaadinp.place.NameToken;
import ru.vaadinp.place.Place;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.*;
import ru.vaadinp.vp.api.NestedMVP;
import ru.vaadinp.vp.api.PlaceMVP;

/**
 * Created by oem on 10/27/16.
 */
public class NestedMVPBuilder {
	private Lazy<? extends ViewImpl<?>> lazyView;
	private Lazy<? extends BaseNestedPresenter<?>> lazyPresenter;
	private final NestedMVP<? extends BaseNestedPresenter<?>> parent;
	private RootMVP rootMVP;
	private NestedSlot revealIn;
	private String nameToken;

	public NestedMVPBuilder(NestedMVP<? extends BaseNestedPresenter<?>> parent, RootMVP rootMVP, NestedSlot revealIn) {
		this.parent = parent;
		this.rootMVP = rootMVP;
		this.revealIn = revealIn;
	}

	public NestedMVPBuilder withView(Lazy<? extends ViewImpl<?>> lazyView) {
		this.lazyView = lazyView;
		return this;
	}

	public NestedMVPBuilder withPresenter(Lazy<? extends BaseNestedPresenter<?>> lazyPresenter) {
		this.lazyPresenter = lazyPresenter;
		return this;
	}

	public NestedMVPBuilder withNameToken(String nameToken) {
		this.nameToken = nameToken;
		return this;
	}

	public PlaceMVP<?> buildPlace() {
		return (PlaceMVP<?>) build();
	}

	public NestedMVP<?> build() {
		if (lazyPresenter == null) {
			lazyView = DoubleCheck.lazy(ViewImpl::new);
			lazyPresenter = DoubleCheck.lazy(() -> new BaseNestedPresenter<View>(lazyView.get(), revealIn));
		}

		NestedMVP<?> mvp;

		if (nameToken == null) {
			mvp = new NestedMVPImpl<>(lazyView, lazyPresenter, rootMVP, null, parent, null);
		} else {
			mvp = new NestedMVPImpl<>(lazyView, lazyPresenter, rootMVP, null, parent, new Place(new NameToken(nameToken, nameToken)));
		}

		return mvp;
	}
}
