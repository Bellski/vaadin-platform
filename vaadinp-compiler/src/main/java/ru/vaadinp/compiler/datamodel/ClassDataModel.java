package ru.vaadinp.compiler.datamodel;

/**
 * Created by Aleksandr on 22.10.2016.
 */
public class ClassDataModel {
    private final String name;
    private final String packageName;
    private final String fqn;

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
