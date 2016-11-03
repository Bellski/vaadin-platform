package ru.vaadinp.place.error;

import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.BaseNestedPresenter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/10/16.
 */
@Singleton
public class BaseErrorPlacePresenter extends BaseNestedPresenter<BaseErrorPlace.View> implements BaseErrorPlace.Presenter {

	@Inject
	public BaseErrorPlacePresenter(BaseErrorPlace.View view, @RevealIn(RootPresenter.class) NestedSlot revealIn) {
		super(view, revealIn);
	}
}
