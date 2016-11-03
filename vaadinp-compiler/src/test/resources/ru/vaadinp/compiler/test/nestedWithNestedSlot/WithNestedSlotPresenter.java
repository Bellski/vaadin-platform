package ru.vaadinp.compiler.test.nestedWithNestedSlot;

import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.GenerateMVPInfo;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.BaseNestedPresenter;

@GenerateMVP
@GenerateMVPInfo(title = "WithNestedSlot")
public class WithNestedSlotPresenter extends BaseNestedPresenter<WithNestedSlot.View> implements WithNestedSlot.Presenter {

	public static final NestedSlot MAIN_SLOT = new NestedSlot();

	public WithNestedSlotPresenter(WithNestedSlot.View view, @RevealIn(RootPresenter.class) NestedSlot nestedSlot) {
		super(view, nestedSlot);
	}
}
