package ru.vaadinp.compiler.datamodel;

/**
 * Created by oem on 10/21/16.
 */
public class GenerateVPComponentModel extends ClassDataModel {
	private AnnotatedPresenterComponent presenterComponent;

	public GenerateVPComponentModel(AnnotatedPresenterComponent presenterComponent) {
		super(presenterComponent.getApiMirror().getName() + "VPComponent", presenterComponent.getPackageName());
		this.presenterComponent = presenterComponent;
	}

	public AnnotatedPresenterComponent getPresenterComponent() {
		return presenterComponent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		GenerateVPComponentModel that = (GenerateVPComponentModel) o;

		return presenterComponent.equals(that.presenterComponent);

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + presenterComponent.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return getName() + ".Declarations.class";
	}
}
