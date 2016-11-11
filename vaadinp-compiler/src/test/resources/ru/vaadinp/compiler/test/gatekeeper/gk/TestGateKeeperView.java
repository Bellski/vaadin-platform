package ru.vaadinp.compiler.test.gatekeeper.gk;

import com.vaadin.ui.Button;
import ru.vaadinp.vp.ViewImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/11/16.
 */
@Singleton
public class TestGateKeeperView extends ViewImpl<TestGateKeeper.Presenter> implements TestGateKeeper.View {

	@Inject
	public TestGateKeeperView() {
		initComponent(new Button("Access", new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				getPresenter().access();
			}
		}));
	}

}
