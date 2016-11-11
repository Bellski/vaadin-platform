package ru.vaadinp.compiler2;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;

/**
 * Created by oem on 11/11/16.
 */
public interface TypeSpecBuilder {
	TypeSpec getTypeSpec();
	ClassName getClassName();
}
