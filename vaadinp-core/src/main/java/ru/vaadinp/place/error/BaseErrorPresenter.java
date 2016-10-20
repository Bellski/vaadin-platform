package ru.vaadinp.place.error;

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
public class BaseErrorPresenter extends NestedPresenter<BaseError.View> implements BaseError.Presenter {

	@Inject
	public BaseErrorPresenter(BaseError.View view,  @RevealIn(RootPresenter.class) NestedSlot revealIn) {
		super(view, revealIn);
	}
}
