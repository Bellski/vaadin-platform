package ru.vaadinp.place.error;

import ru.vaadinp.place.NameToken;

/**
 * Created by oem on 11/3/16.
 */
public class BaseErrorPlaceTokenSet {
	public static final String ENCODED_VAADINPERROR = "!/vaadinp-error";
	public static final String DECODED_VAADINPERROR = "!/vaadinp-error";

	static final NameToken VAADINPERROR_TOKEN = new NameToken(ENCODED_VAADINPERROR, DECODED_VAADINPERROR);
}
