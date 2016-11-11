package ru.vaadinp.compiler.test.gatekeeper;

import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.BaseNestedPresenter;

@GenerateMVP
public class ApplicationPresenter extends BaseNestedPresenter<Application.View> implements Application.Presenter {

	public static final NestedSlot MAIN_SLOT = new NestedSlot();

	public ApplicationPresenter(Application.View view, @RevealIn(RootPresenter.class) NestedSlot nestedSlot) {
		super(view, nestedSlot);
	}
}
