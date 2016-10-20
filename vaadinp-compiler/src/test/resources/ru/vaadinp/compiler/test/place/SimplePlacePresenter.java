package ru.vaadinp.compiler.test.place;

import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.GenerateVPComponent;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.PresenterComponent;

@GenerateVPComponent
public class SimplePlacePresenter extends NestedPresenter<SimplePlace.View> implements SimplePlace.Presenter {

	@GenerateNameToken
	public static final String NAME_TOKEN = "!/simple";

	public SimplePlacePresenter(SimplePlace.View view, @RevealIn(RootPresenter.class) NestedSlot inSlot) {
		super(view, inSlot);
	}
}
