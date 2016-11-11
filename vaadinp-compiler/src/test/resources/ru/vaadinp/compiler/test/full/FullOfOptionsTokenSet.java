package ru.vaadinp.compiler.test.full;

import ru.vaadinp.place.NameToken;

/**
 * Created by Aleksandr on 06.11.2016.
 */
public class FullOfOptionsTokenSet {
    public static final String ENCODED_FULLOFOPTIONS = "!/fullOfOptions";
    public static final String DECODED_FULLOFOPTIONS = "!/fullOfOptions";

    static final NameToken FULLOFOPTIONS_TOKEN = new NameToken(ENCODED_FULLOFOPTIONS, DECODED_FULLOFOPTIONS);

    public static final String ENCODED_FULLOFOPTIONS_SOMEPARAMETER = "!/fullOfOptions/?";
    public static final String DECODED_FULLOFOPTIONS_SOMEPARAMETER = "!/fullOfOptions/{someParameter}";

    static final NameToken FULLOFOPTIONS_SOMEPARAMETER_TOKEN = new NameToken(ENCODED_FULLOFOPTIONS_SOMEPARAMETER, DECODED_FULLOFOPTIONS_SOMEPARAMETER, new String[] {"someParameter"}, new int[] {2});
}
