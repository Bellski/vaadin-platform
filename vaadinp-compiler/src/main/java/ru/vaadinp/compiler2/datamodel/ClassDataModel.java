package ru.vaadinp.compiler2.datamodel;

import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aleksandr on 22.10.2016.
 */
public class ClassDataModel {
    private final String name;
    private final String packageName;
    private final String fqn;

    private Set<String> imports = new HashSet<>();

    public ClassDataModel(String name, String packageName) {
        this(name, packageName, packageName + "." + name);
    }

    public ClassDataModel(String name, String packageName, String fqn) {
        this.name = name;
        this.packageName = packageName;
        this.fqn = fqn;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return name + ".class";
    }

    public String getPackageName() {
        return packageName;
    }

    public String getFqn() {
        return fqn;
    }

    public Set<String> getImports() {
        return imports;
    }

    public void addImport(String fqn) {
        imports.add(fqn);
    }

    public void importClass(Class<?> clazz) {
        imports.add(clazz.getName());
    }

    public void importClassDataModel(ClassDataModel classDataModel) {
        imports.add(classDataModel.getFqn());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassDataModel that = (ClassDataModel) o;

        return fqn.equals(that.fqn);

    }

    @Override
    public int hashCode() {
        return fqn.hashCode();
    }
}
