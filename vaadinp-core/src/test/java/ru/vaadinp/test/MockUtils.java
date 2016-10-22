package ru.vaadinp.test;

import dagger.Lazy;
import dagger.internal.DoubleCheck;
import ru.vaadinp.place.error.BaseErrorPlaceVPComponent;
import ru.vaadinp.place.error.BaseErrorPresenter;
import ru.vaadinp.place.error.BaseErrorView;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceVPComponent;
import ru.vaadinp.place.notfound.BaseNotFoundPresenter;
import ru.vaadinp.place.notfound.BaseNotFoundView;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.SlotRevealBus;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.slot.root.RootVPComponent;
import ru.vaadinp.slot.root.RootView;
import ru.vaadinp.vp.*;

/**
 * Created by oem on 10/14/16.
 */
public class MockUtils {

	public static RootVPComponent mockRootVP() {
		final Lazy<RootView> lazyView = DoubleCheck.lazy(() -> new RootView(new UIMock()));
		final RootVPComponent vpComponent = new RootVPComponent(DoubleCheck.lazy(() -> new RootPresenter(lazyView.get())), lazyView){
			@Override
			public RootPresenter getPresenter() {
				final RootPresenter presenter = super.getPresenter();

				presenter.setVpComponent(this);

				return presenter;
			}
		};

		return vpComponent;
	}

	public static BaseNotFoundPlaceVPComponent mockBaseNotFoundVP(RootVPComponent rootVPComponent) {
		final Lazy<BaseNotFoundView> lazyView = DoubleCheck.lazy(BaseNotFoundView::new);

		return new BaseNotFoundPlaceVPComponent(DoubleCheck.lazy(() -> new BaseNotFoundPresenter(lazyView.get(), RootPresenter.ROOT_SLOT)), lazyView, rootVPComponent) {
			@Override
			public BaseNotFoundPresenter getPresenter() {
				BaseNotFoundPresenter baseNotFoundPresenter = super.getPresenter();

				baseNotFoundPresenter.setVpComponent(this);

				return baseNotFoundPresenter;
			}
		};
	}

	public static BaseErrorPlaceVPComponent mockBaseBaseErrorPlaceVP(RootVPComponent rootVPComponent) {
		final Lazy<BaseErrorView> lazyView = DoubleCheck.lazy(BaseErrorView::new);

		return new BaseErrorPlaceVPComponent(DoubleCheck.lazy(() -> new BaseErrorPresenter(lazyView.get(), RootPresenter.ROOT_SLOT)), lazyView, rootVPComponent) {
			@Override
			public BaseErrorPresenter getPresenter() {
				BaseErrorPresenter baseErrorPresenter = super.getPresenter();

				baseErrorPresenter.setVpComponent(this);

				return baseErrorPresenter;
			}
		};
	}

	public static NestedVPComponent<?, ?> mockNestedVP(String nameToken,
													   NestedVPComponent<?, ?> parent,
													   NestedSlot nestedSlot) {
		return mockNestedVP(nameToken, null, null, parent, nestedSlot);
	}


	public static NestedVPComponent<?, ?> mockNestedVP(String nameToken,
													   Lazy<NestedPresenter<?>> presenter,
													   NestedVPComponent<?, ?> parent,
													   NestedSlot nestedSlot) {
		return mockNestedVP(nameToken, null, presenter, parent, nestedSlot);
	}

	public static NestedVPComponent<?, ?> mockNestedVP(NestedVPComponent<?, ?> parent,
													   NestedSlot nestedSlot) {
		return mockNestedVP(null, null, null, parent, nestedSlot);
	}

	public static NestedVPComponent<?, ?> mockNestedVP(Lazy<NestedPresenter<?>> presenter,
													   NestedVPComponent<?, ?> parent,
													   NestedSlot nestedSlot) {
		return mockNestedVP(null, null, presenter, parent, nestedSlot);
	}

	public static NestedVPComponent<?, ?> mockNestedVP(Lazy<ViewImpl<?>> view,
													   Lazy<NestedPresenter<?>> presenter,
													   NestedVPComponent<?, ?> parent,
													   NestedSlot nestedSlot) {

		return mockNestedVP(null, view, presenter, parent, nestedSlot);
	}

	public static NestedVPComponent<?, ?> mockNestedVP(String nameToken,
													   Lazy<ViewImpl<?>> view,
													   Lazy<NestedPresenter<?>> presenter,
													   NestedVPComponent<?, ?> parent,
													   NestedSlot nestedSlot) {

		if (view == null) {
			view = DoubleCheck.lazy(ViewImpl::new);
		}

		if (presenter == null) {
			final Lazy<ViewImpl<?>> finalView = view;
			presenter = DoubleCheck.lazy(() -> new NestedPresenter<View>(finalView.get(), nestedSlot));
		}

		NestedVPComponent<?, ?> nestedVPComponent;

		if (nameToken == null) {
			nestedVPComponent = new NestedVPComponent(presenter, view, parent) {
				@Override
				public PresenterComponent<?> getPresenter() {
					final PresenterComponent presenter = super.getPresenter();

					presenter.setVpComponent(this);

					return presenter;
				}
			};
		} else {
			nestedVPComponent = new PlaceVPComponent(nameToken, presenter, view, parent) {
				@Override
				public PresenterComponent<?> getPresenter() {
					final PresenterComponent presenter = super.getPresenter();

					presenter.setVpComponent(this);

					return presenter;
				}
			};
		}

		return nestedVPComponent;
	}

}
