package ru.vaadinp.compiler.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by oem on 10/16/16.
 */
public class JavaClassModel {
	private final String packageName;
	private final String className;
	private final String qualifiedName;

	private final Set<JavaClassModel> imports = new HashSet<>();

	public JavaClassModel(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
		this.qualifiedName = packageName + "." + className;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public void importClass(JavaClassModel javaClassModel) {
		imports.add(javaClassModel);
	}

	public Set<JavaClassModel> getImports() {
		return imports;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		JavaClassModel that = (JavaClassModel) o;

		return qualifiedName.equals(that.qualifiedName);

	}

	@Override
	public int hashCode() {
		return qualifiedName.hashCode();
	}
}
