package ru.vaadinp.annotations.dagger;

import ru.vaadinp.vp.NestedPresenter;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by oem on 10/11/16.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface RevealIn {
	Class<? extends NestedPresenter<?>> value();
}
