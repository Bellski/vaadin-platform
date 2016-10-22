package ru.vaadinp.compiler.datamodel;

/**
 * Created by oem on 10/21/16.
 */
public class Parent {
	private final String name;
	private final String fqn;

	public Parent(String name, String fqn) {
		this.name = name;
		this.fqn = fqn;
	}

	public String getName() {
		return name;
	}

	public String getFqn() {
		return fqn;
	}
}
