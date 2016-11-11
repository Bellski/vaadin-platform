package ru.vaadinp.compiler.test.full;

import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.GenerateMVPInfo;
import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootPresenter;
import ru.vaadinp.vp.BaseNestedPresenter;

@GenerateMVP(
        nameTokens = {
                @GenerateNameToken(nameToken = "!/fullOfOptions"),
                @GenerateNameToken(nameToken = "!/fullOfOptions/{someParameter}")
        },
        mvpInfo = @GenerateMVPInfo(
                caption = "caption",
                title = "title",
                historyToken = "!/fullOfOptions",
                priority = 0
        )
)
public class FullOfOptionsPresenter extends BaseNestedPresenter<FullOfOptions.View> implements FullOfOptions.Presenter {

    public FullOfOptionsPresenter(FullOfOptions.View view, @RevealIn(RootPresenter.class) NestedSlot nestedSlot) {
        super(view, nestedSlot);
    }
}
