package ru.vaadinp.annotations;

import ru.vaadinp.security.Gatekeeper;

import java.lang.annotation.*;

/**
 * Created by oem on 10/16/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface GenerateMVP {
	GenerateNameToken[] nameTokens() default {};
	GenerateMVPInfo mvpInfo() default @GenerateMVPInfo(priority = -2);
	Class<? extends Gatekeeper> gateKeeper() default Gatekeeper.class;
}
