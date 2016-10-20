package ru.vaadinp.compiler.test.nested;

import ru.vaadinp.annotations.GenerateVPComponent;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.NestedPresenter;

@GenerateVPComponent
public class SimpleNestedPresenter extends NestedPresenter<SimpleNested.View> implements SimpleNested.Presenter {
	public SimpleNestedPresenter(SimpleNested.View view, @RevealIn(RootPresenter.class) NestedSlot nestedSlot) {
		super(view, nestedSlot);
	}
}
