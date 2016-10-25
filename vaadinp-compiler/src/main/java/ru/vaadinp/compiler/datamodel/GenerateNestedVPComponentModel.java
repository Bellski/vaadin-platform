package ru.vaadinp.compiler.datamodel;

import javax.lang.model.element.TypeElement;

/**
 * Created by Aleksandr on 22.10.2016.
 */
public class GenerateNestedVPComponentModel extends GenerateVPComponentModel {
    private TypeElement parentPresenterElement;
    private GenerateNestedVPComponentModel parent;
    private boolean isRoot;

    public GenerateNestedVPComponentModel(AnnotatedNestedPresenter nestedPresenter) {
        super(nestedPresenter);
    }

    @Override
    public AnnotatedNestedPresenter getPresenterComponent() {
        return (AnnotatedNestedPresenter) super.getPresenterComponent();
    }

    public GenerateNestedVPComponentModel getParent() {
        return parent;
    }

    public void setParent(GenerateNestedVPComponentModel parent) {
        this.parent = parent;
    }

    public TypeElement getParentPresenterElement() {
        return parentPresenterElement;
    }

    public void setParentPresenterElement(TypeElement parentPresenterElement) {
        this.parentPresenterElement = parentPresenterElement;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public boolean isRoot() {
        return isRoot;
    }
}
