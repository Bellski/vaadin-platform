package ru.vaadinp.compiler2.test.component;

import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.vp.PresenterComponent;

@GenerateMVP
public class SimpleComponentPresenter extends PresenterComponent<SimpleComponent.View> implements SimpleComponent.Presenter {
	public SimpleComponentPresenter(SimpleComponent.View view) {
		super(view);
	}
}
