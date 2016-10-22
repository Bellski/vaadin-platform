package ru.vaadinp.compiler.processors;

import com.sun.tools.javac.code.Symbol;

import javax.lang.model.element.TypeElement;

/**
 * Created by oem on 10/21/16.
 */
public class ApiMirror {
	private final String name;
	private final String packageName;
	private final String fqn;

	private final TypeElement apiElement;

	public ApiMirror(TypeElement apiElement) {
		this.apiElement = apiElement;
		this.name = apiElement.getSimpleName().toString();
		this.packageName = Symbol.class.cast(apiElement).packge().getQualifiedName().toString();
		this.fqn = apiElement.getQualifiedName().toString();
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

	public TypeElement getApiElement() {
		return apiElement;
	}

	public String getPresenterApiName() {
		return name + ".Presenter";
	}

	public String getViewApiName() {
		return name + ".View";
	}
}
