package ru.vaadinp.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by oem on 10/16/16.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface GenerateNameToken {
	String value() default "";
}
