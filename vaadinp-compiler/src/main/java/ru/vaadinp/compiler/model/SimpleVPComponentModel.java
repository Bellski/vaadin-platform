package ru.vaadinp.compiler.model;

/**
 * Created by oem on 10/16/16.
 */
public class SimpleVPComponentModel extends JavaClassModel {
	private final JavaClassModel presenterImpl;
	private final JavaClassModel viewImpl;

	private final String classModuleName;
	private String viewApiClassName;
	private String presenterApiClassName;

	public SimpleVPComponentModel(String packageName, String vpComponentClassName, JavaClassModel presenterImpl, JavaClassModel viewImpl) {
		super(packageName, vpComponentClassName + "VPComponent");
		this.presenterImpl = presenterImpl;
		this.viewImpl = viewImpl;
		this.classModuleName = vpComponentClassName + "Module";
	}

	public String getPresenterName() {
		return presenterImpl.getClassName();
	}

	public String getViewName() {
		return viewImpl.getClassName();
	}

	public String getClassModuleName() {
		return classModuleName;
	}

	public void setViewApiClassName(String viewApiClassName) {
		this.viewApiClassName = viewApiClassName;
	}

	public void setPresenterApiClassName(String presenterApiClassName) {
		this.presenterApiClassName = presenterApiClassName;
	}

	public String getViewApiClassName() {
		return viewApiClassName;
	}

	public String getPresenterApiClassName() {
		return presenterApiClassName;
	}
}
