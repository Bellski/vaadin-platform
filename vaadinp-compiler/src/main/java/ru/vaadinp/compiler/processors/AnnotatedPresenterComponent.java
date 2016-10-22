package ru.vaadinp.compiler.processors;

import com.sun.tools.javac.code.Symbol;

import javax.lang.model.element.TypeElement;

/**
 * Created by oem on 10/21/16.
 */
public class AnnotatedPresenterComponent {
	private final String name;
	private final String packageName;
	private final String fqn;

	private final TypeElement presenterElement;

	private final ApiMirror apiMirror;
	private final ViewMirror viewMirror;

	public AnnotatedPresenterComponent(TypeElement presenterElement, ApiMirror apiMirror, ViewMirror viewMirror) {
		this.presenterElement = presenterElement;
		this.name = presenterElement.getSimpleName().toString();
		this.packageName = Symbol.class.cast(presenterElement).packge().getQualifiedName().toString();
		this.fqn = presenterElement.getQualifiedName().toString();
		this.apiMirror = apiMirror;
		this.viewMirror = viewMirror;
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

	public TypeElement getPresenterElement() {
		return presenterElement;
	}

	public ApiMirror getApiMirror() {
		return apiMirror;
	}

	public ViewMirror getViewMirror() {
		return viewMirror;
	}

}
