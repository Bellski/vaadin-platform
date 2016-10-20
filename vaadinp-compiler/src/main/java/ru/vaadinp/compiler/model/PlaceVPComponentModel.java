package ru.vaadinp.compiler.model;

/**
 * Created by oem on 10/20/16.
 */
public class PlaceVPComponentModel extends NestedVPComponentModel {

	private String namePlace;

	public PlaceVPComponentModel(String packageName, String vpComponentClassName, JavaClassModel presenterImpl, JavaClassModel viewImpl) {
		super(packageName, vpComponentClassName, presenterImpl, viewImpl);
	}

	public String getNamePlace() {
		return namePlace;
	}

	public void setNamePlace(String namePlace) {
		this.namePlace = namePlace;
	}
}
