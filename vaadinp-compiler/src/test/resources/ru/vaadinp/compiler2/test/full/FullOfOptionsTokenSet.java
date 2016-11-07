package ru.vaadinp.compiler2.test.full;

import ru.vaadinp.place.NameToken;

/**
 * Created by Aleksandr on 06.11.2016.
 */
public class FullOfOptionsTokenSet {
    public static final String ENCODED_FULLOFOPTIONS = "!/fullOfOptions";
    public static final String DECODED_FULLOFOPTIONS = "!/fullOfOptions";

    static final NameToken FULLOFOPTIONS_TOKEN = new NameToken(ENCODED_FULLOFOPTIONS, DECODED_FULLOFOPTIONS);

    public static final String ENCODED_FULLOFOPTIONS__SOMEPARAMETER_ = "!/fullOfOptions/?";
    public static final String DECODED_FULLOFOPTIONS__SOMEPARAMETER_ = "!/fullOfOptions/{someParameter}";

    static final NameToken FULLOFOPTIONS__SOMEPARAMETER__TOKEN = new NameToken(ENCODED_FULLOFOPTIONS__SOMEPARAMETER_, DECODED_FULLOFOPTIONS__SOMEPARAMETER_, new String[] {"someParameter"}, new int[] {2});
}
