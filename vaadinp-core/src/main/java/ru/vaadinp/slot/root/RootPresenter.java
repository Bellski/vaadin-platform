package ru.vaadinp.slot.root;

import com.vaadin.ui.Component;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.PresenterComponent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;

/**
 * Created by oem on 10/10/16.
 */
@Singleton
public class RootPresenter extends BaseNestedPresenter<Root.View> implements Root.Presenter {
	public static final NestedSlot ROOT_SLOT = new NestedSlot();

	@Inject
	public RootPresenter(RootView view) {
		super(view, null);
		setVisible(true);
	}

	@Override
	public Component asComponent() {
		throw new IllegalStateException("Root getView has no Component, you should never call asComponent()");
	}

	@Override
	public void forceReveal() {

	}

	public void reset() {
		internalReset();
	}
}
