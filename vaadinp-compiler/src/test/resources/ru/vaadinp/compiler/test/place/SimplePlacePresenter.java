package ru.vaadinp.compiler.test.place;

import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.BaseNestedPresenter;

@GenerateMVP(
		nameTokens = {
				@GenerateNameToken(nameToken = "!/simple")
		}
)
public class SimplePlacePresenter extends BaseNestedPresenter<SimplePlace.View> implements SimplePlace.Presenter {

	public SimplePlacePresenter(SimplePlace.View view, @RevealIn(RootPresenter.class) NestedSlot inSlot) {
		super(view, inSlot);
	}
}
