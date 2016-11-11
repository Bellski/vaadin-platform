package ru.vaadinp.compiler.test.gatekeeper.home;

import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.compiler.test.gatekeeper.ApplicationPresenter;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.BaseNestedPresenter;

@GenerateMVP(
	nameTokens = @GenerateNameToken(nameToken = "!/home", isDefault = true)
)
public class HomePresenter extends BaseNestedPresenter<Home.View> implements Home.Presenter {

	public HomePresenter(Home.View view, @RevealIn(ApplicationPresenter.class) NestedSlot nestedSlot) {
		super(view, nestedSlot);
	}
}
