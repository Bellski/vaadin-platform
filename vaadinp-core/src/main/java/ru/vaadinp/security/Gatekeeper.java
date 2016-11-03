package ru.vaadinp.security;

import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.api.NestedMVP;

/**
 * Created by oem on 10/7/16.
 */
public interface Gatekeeper {
	void canReveal(NestedMVP<? extends BaseNestedPresenter<?>> mvp);
}
