package ru.vaadinp.place.notfound;

import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.NestedPresenter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/10/16.
 */
@Singleton
public class BaseNotFoundPlacePresenter extends NestedPresenter<BaseNotFoundPlace.View> implements BaseNotFoundPlace.Presenter {

	public static final String NAME_TOKEN = "!/vaadinp-notfound";

	@Inject
	public BaseNotFoundPlacePresenter(BaseNotFoundPlace.View view, @RevealIn(RootPresenter.class) NestedSlot nestedSlot) {
		super(view, nestedSlot);
	}
}
