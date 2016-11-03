package ru.vaadinp.annotations;

import java.lang.annotation.*;

/**
 * Created by oem on 10/16/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface GenerateNameToken {
	String nameToken();
	boolean isDefault() default false;
	boolean isError() default false;
	boolean isNotFound() default false;
}
