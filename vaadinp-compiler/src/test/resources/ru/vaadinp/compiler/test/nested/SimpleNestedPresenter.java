package ru.vaadinp.compiler.test.nested;

import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.BaseNestedPresenter;

@GenerateMVP
public class SimpleNestedPresenter extends BaseNestedPresenter<SimpleNested.View> implements SimpleNested.Presenter {

	public SimpleNestedPresenter(SimpleNested.View view, @RevealIn(RootPresenter.class) NestedSlot nestedSlot) {
		super(view, nestedSlot);
	}
}
