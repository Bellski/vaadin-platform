package ru.vaadinp.compiler.test.nestedWithNestedSlot;

import ru.vaadinp.annotations.GenerateVPComponent;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.NestedPresenter;

@GenerateVPComponent
public class WithNestedSlotPresenter extends NestedPresenter<WithNestedSlot.View> implements WithNestedSlot.Presenter {

	public static final NestedSlot MAIN_SLOT = new NestedSlot();

	public WithNestedSlotPresenter(WithNestedSlot.View view, @RevealIn(RootPresenter.class) NestedSlot nestedSlot) {
		super(view, nestedSlot);
	}
}
