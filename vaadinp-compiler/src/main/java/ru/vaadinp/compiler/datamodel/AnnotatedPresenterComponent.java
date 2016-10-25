package ru.vaadinp.compiler.datamodel;

import com.sun.tools.javac.code.Symbol;

import javax.lang.model.element.TypeElement;

/**
 * Created by oem on 10/21/16.
 */
public class AnnotatedPresenterComponent extends ClassDataModel {
	private final TypeElement presenterElement;

	private final ApiMirror apiMirror;
	private final ViewMirror viewMirror;

	public AnnotatedPresenterComponent(TypeElement presenterElement, ApiMirror apiMirror, ViewMirror viewMirror) {
		super(
				presenterElement.getSimpleName().toString(),
				Symbol.class.cast(presenterElement).packge().getQualifiedName().toString(),
				presenterElement.getQualifiedName().toString()
		);

		this.presenterElement = presenterElement;
		this.apiMirror = apiMirror;
		this.viewMirror = viewMirror;
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
