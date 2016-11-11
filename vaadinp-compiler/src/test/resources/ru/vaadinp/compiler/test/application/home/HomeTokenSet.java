package ru.vaadinp.compiler.test.application.home;

import ru.vaadinp.place.NameToken;

/**
 * Created by Aleksandr on 06.11.2016.
 */
public class HomeTokenSet {
    public static final String ENCODED_HOME = "!/home";
    public static final String DECODED_HOME = "!/home";

    static final NameToken HOME_TOKEN = new NameToken(ENCODED_HOME, DECODED_HOME);
}
