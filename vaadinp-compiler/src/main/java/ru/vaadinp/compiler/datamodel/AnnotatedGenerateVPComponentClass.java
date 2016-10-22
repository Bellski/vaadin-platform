package ru.vaadinp.compiler.datamodel;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Created by oem on 10/20/16.
 */
public class AnnotatedGenerateVPComponentClass {
	private final String presenterImplName;
	private String componentName;
	private final String packageName;
	private TypeElement classElement;

	private String slotName;
	private boolean hasSlot;
	private boolean hasPlace;

	private TypeElement apiClassElement;

	private TypeElement viewImplClassElement;

	private VariableElement slotElement;

	private VariableElement nameTokenElement;

	private boolean hasNameToken;

	private boolean hasParent;
	private TypeElement parent;


	public AnnotatedGenerateVPComponentClass(TypeElement classElement) {
		presenterImplName = classElement.getSimpleName().toString();
		packageName =  Symbol.class.cast(classElement).packge().toString();

		this.classElement = classElement;
	}

	public String getPresenterImplName() {
		return presenterImplName;
	}

	public String getComponentName() {
		return componentName;
	}

	public String getPackageName() {
		return packageName;
	}

	public TypeElement getClassElement() {
		return classElement;
	}

	public String getSlotName() {
		return slotName;
	}


	public boolean hasSlot() {
		return hasSlot;
	}

	public boolean hasPlace() {
		return hasPlace;
	}

	public void setHasPlace(boolean hasPlace) {
		this.hasPlace = hasPlace;
	}

	public String getQualifiedName() {
		return packageName + "." + componentName;
	}

	public TypeElement getApiClassElement() {
		return apiClassElement;
	}

	public void setApiClassElement(TypeElement apiClassElement) {
		this.apiClassElement = apiClassElement;
		this.componentName = apiClassElement.getSimpleName() + "VPComponent";
	}

	public TypeElement getViewImplClassElement() {
		return viewImplClassElement;
	}

	public void setViewImplClassElement(TypeElement viewImplClassElement) {
		this.viewImplClassElement = viewImplClassElement;
	}

	public String getViewImplName() {
		return viewImplClassElement.getSimpleName().toString();
	}

	public String getViewApiName() {
		return apiClassElement.getSimpleName().toString() + ".View";
	}

	public String getPresenterApiName() {
		return apiClassElement.getSimpleName().toString() + ".Presenter";
	}

	public VariableElement getSlotElement() {
		return slotElement;
	}

	public void setSlotElement(VariableElement slotElement) {
		if (slotElement != null) {
			this.slotElement = slotElement;
			this.hasSlot = true;
			this.slotName = slotElement.getSimpleName().toString();
		}
	}

	public VariableElement getNameTokenElement() {
		return nameTokenElement;
	}

	public void setNameTokenElement(VariableElement nameTokenElement) {
		if (nameTokenElement != null) {
			this.nameTokenElement = nameTokenElement;
			this.hasNameToken = true;
		}
	}

	public boolean hasNameToken() {
		return hasNameToken;
	}

	public boolean hasParent() {
		return hasParent;
	}

	public String getParentQualifiedName() {
		return parent.getQualifiedName().toString();
	}

	public String getParentName() {
		return parent.getSimpleName().toString();
	}

	public void setParent(TypeElement parent) {
		if (parent != null) {
			this.parent = parent;
			this.hasParent = true;
		}
	}
}
