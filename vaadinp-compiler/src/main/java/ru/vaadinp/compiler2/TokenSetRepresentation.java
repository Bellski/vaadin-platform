package ru.vaadinp.compiler2;

import com.squareup.javapoet.ClassName;

import java.util.Collection;

/**
 * Created by oem on 11/11/16.
 */
public interface TokenSetRepresentation {
	ClassName getClassName();
	Collection<TokenRepresentation> getTokenRepresentations();
}
