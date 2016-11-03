package ru.vaadinp.compiler.test.simple;

import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.vp.PresenterComponent;

@GenerateMVP
public class SimplePresenter extends PresenterComponent<Simple.View> implements Simple.Presenter {
	public SimplePresenter(Simple.View view) {
		super(view);
	}
}
