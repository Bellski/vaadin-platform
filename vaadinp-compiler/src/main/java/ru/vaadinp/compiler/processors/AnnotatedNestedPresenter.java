package ru.vaadinp.compiler.processors;

import javax.lang.model.element.TypeElement;

/**
 * Created by oem on 10/21/16.
 */
public class AnnotatedNestedPresenter extends AnnotatedPresenterComponent {
	private AnnotatedNestedPresenter parent;

	private NestedSlotMirror nestedSlotMirror;
	private String nameToken;

	public AnnotatedNestedPresenter(TypeElement presenterElement, ApiMirror apiMirror, ViewMirror viewMirror) {
		super(presenterElement, apiMirror, viewMirror);
	}

	public AnnotatedNestedPresenter getParent() {
		return parent;
	}

	public void setParent(AnnotatedNestedPresenter parent) {
		this.parent = parent;
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
}
