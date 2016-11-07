package ru.vaadinp.annotations;

import java.lang.annotation.*;

/**
 * Created by oem on 10/16/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface GenerateMVPInfo {
	String caption() default "";
	String title() default "";
	String historyToken() default "";
	String icon() default "";
	int priority() default -1;
}
