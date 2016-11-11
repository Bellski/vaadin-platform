package ru.vaadinp.place.notfound;

import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.MVPInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/10/16.
 */
@Singleton
@GenerateMVP(
	nameTokens = @GenerateNameToken(nameToken = "!/vaadinp-notfound")
)
public class BaseNotFoundPlacePresenter extends BaseNestedPresenter<BaseNotFoundPlace.View> implements BaseNotFoundPlace.Presenter {

	@Inject
	public BaseNotFoundPlacePresenter(BaseNotFoundPlace.View view, @RevealIn(RootPresenter.class) NestedSlot nestedSlot) {
		super(view, nestedSlot);
	}
}
