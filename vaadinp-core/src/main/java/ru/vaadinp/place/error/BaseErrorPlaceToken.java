package ru.vaadinp.place.error;

import ru.vaadinp.place.NameToken;

/**
 * Created by oem on 11/3/16.
 */
public class BaseErrorPlaceToken {
	public static final String ENCODED_VAADINP_ERROR = "!/vaadinp-error";
	public static final String DECODED_VAADINP_ERROR = "!/vaadinp-error";

	static final NameToken VAADINP_ERROR_NAME_TOKEN = new NameToken(ENCODED_VAADINP_ERROR, DECODED_VAADINP_ERROR);
}
