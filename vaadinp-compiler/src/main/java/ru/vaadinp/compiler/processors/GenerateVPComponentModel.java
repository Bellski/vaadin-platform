package ru.vaadinp.compiler.processors;

/**
 * Created by oem on 10/21/16.
 */
public class GenerateVPComponentModel {
	private AnnotatedPresenterComponent presenterComponent;

	private final String name;
	private final String packageName;
	private final String fqn;

	public GenerateVPComponentModel(AnnotatedPresenterComponent presenterComponent) {
		this.presenterComponent = presenterComponent;
		this.name = presenterComponent.getApiMirror().getName() + "VPComponent";
		this.fqn = presenterComponent.getPackageName() + "." + this.name;
		this.packageName = presenterComponent.getPackageName();
	}

	public AnnotatedPresenterComponent getPresenterComponent() {
		return presenterComponent;
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
}
