package ru.vaadinp.compiler.datamodel;

import javax.lang.model.element.TypeElement;

/**
 * Created by oem on 10/21/16.
 */
public class AnnotatedNestedPresenter extends AnnotatedPresenterComponent {

	private NestedSlotMirror nestedSlotMirror;
	private String nameToken;
	boolean isDefault;
	boolean isNotFound;
	boolean isError;

	public AnnotatedNestedPresenter(TypeElement presenterElement, ApiMirror apiMirror, ViewMirror viewMirror) {
		super(presenterElement, apiMirror, viewMirror);
	}

	public NestedSlotMirror getNestedSlotMirror() {
		return nestedSlotMirror;
	}

	public void setNestedSlotMirror(NestedSlotMirror nestedSlotMirror) {
		this.nestedSlotMirror = nestedSlotMirror;
	}

	public String getNameToken() {
		return nameToken;
	}

	public void setNameToken(String nameToken) {
		this.nameToken = nameToken;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean aDefault) {
		isDefault = aDefault;
	}

	public boolean isNotFound() {
		return isNotFound;
	}

	public void setNotFound(boolean notFound) {
		isNotFound = notFound;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean error) {
		isError = error;
	}
}
