package ru.vaadinp.security;

import ru.vaadinp.vp.VPComponent;

/**
 * Created by oem on 10/7/16.
 */
public interface Gatekeeper {
	void canReveal(VPComponent<?, ?> vpComponent);
}
