package ru.vaadinp.compiler.test.place;

import ru.vaadinp.place.NameToken;

/**
 * Created by oem on 11/3/16.
 */
public class SimplePlaceToken {
	public static final String ENCODED_SIMPLE = "!/simple";
	public static final String DECODED_SIMPLE = "!/simple";

	static final NameToken SIMPLE_TOKEN = new NameToken(ENCODED_SIMPLE, DECODED_SIMPLE);
}
