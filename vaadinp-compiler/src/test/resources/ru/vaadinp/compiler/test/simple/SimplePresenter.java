package ru.vaadinp.compiler.test.simple;

import ru.vaadinp.annotations.GenerateVPComponent;
import ru.vaadinp.vp.PresenterComponent;

@GenerateVPComponent
public class SimplePresenter extends PresenterComponent<Simple.View> implements Simple.Presenter {
	public SimplePresenter(Simple.View view) {
		super(view);
	}
}
