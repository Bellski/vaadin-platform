package ru.vaadinp.compiler.test.gatekeeper.gk;

/**
 * Created by oem on 10/11/16.
 */
public interface TestGateKeeper {
	interface View extends ru.vaadinp.vp.View {

	}

	interface Presenter extends ru.vaadinp.vp.api.Presenter {
		void access();
	}
}
