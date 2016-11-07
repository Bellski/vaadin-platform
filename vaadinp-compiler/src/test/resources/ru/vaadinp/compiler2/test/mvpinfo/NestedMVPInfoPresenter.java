package ru.vaadinp.compiler2.test.mvpinfo;

import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.GenerateMVPInfo;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.BaseNestedPresenter;

@GenerateMVP(
		mvpInfo = @GenerateMVPInfo(title = "title", historyToken = "!/historyToken")
)
public class NestedMVPInfoPresenter extends BaseNestedPresenter<NestedMVPInfo.View> implements NestedMVPInfo.Presenter {

	public NestedMVPInfoPresenter(NestedMVPInfo.View view, @RevealIn(RootPresenter.class) NestedSlot nestedSlot) {
		super(view, nestedSlot);
	}
}
