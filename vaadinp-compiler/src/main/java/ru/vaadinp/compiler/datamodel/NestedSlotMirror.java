package ru.vaadinp.compiler.datamodel;

import javax.lang.model.element.VariableElement;

/**
 * Created by oem on 10/21/16.
 */
public class NestedSlotMirror {
	private final String name;

	public NestedSlotMirror(VariableElement nestedSlotElement) {
		name = nestedSlotElement.getSimpleName().toString();
	}

	public String getName() {
		return name;
	}
}
