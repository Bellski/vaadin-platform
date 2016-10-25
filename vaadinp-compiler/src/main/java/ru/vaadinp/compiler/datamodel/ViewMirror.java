package ru.vaadinp.compiler.datamodel;

import com.sun.tools.javac.code.Symbol;

import javax.lang.model.element.TypeElement;

/**
 * Created by oem on 10/21/16.
 */
public class ViewMirror {
	private final String name;
	private final String packageName;
	private final String fqn;

	private final TypeElement viewElement;

	public ViewMirror(TypeElement viewElement) {
		this.viewElement = viewElement;
		this.name = viewElement.getSimpleName().toString();
		this.packageName = Symbol.class.cast(viewElement).packge().getQualifiedName().toString();
		this.fqn = viewElement.getQualifiedName().toString();
	}

	public String getName() {
		return name;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getFqn() {
		return fqn;
	}

	public TypeElement getViewElement() {
		return viewElement;
	}
}
