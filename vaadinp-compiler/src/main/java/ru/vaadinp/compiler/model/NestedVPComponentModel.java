package ru.vaadinp.compiler.model;

/**
 * Created by oem on 10/18/16.
 */
public class NestedVPComponentModel extends SimpleVPComponentModel {
	private boolean hasNestedSlot;
	private String slotName;

	public NestedVPComponentModel(String packageName, String vpComponentClassName, JavaClassModel presenterImpl, JavaClassModel viewImpl) {
		super(packageName, vpComponentClassName, presenterImpl, viewImpl);
	}

	public boolean isHasNestedSlot() {
		return hasNestedSlot;
	}

	public void setHasNestedSlot(boolean hasNestedSlot) {
		this.hasNestedSlot = hasNestedSlot;
	}

	public String getSlotName() {
		return slotName;
	}

	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}
}

