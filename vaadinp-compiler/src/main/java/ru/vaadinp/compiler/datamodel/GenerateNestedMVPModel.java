package ru.vaadinp.compiler.datamodel;

import ru.vaadinp.annotations.GenerateMVPInfo;

import javax.lang.model.element.TypeElement;

/**
 * Created by Aleksandr on 22.10.2016.
 */
public class GenerateNestedMVPModel extends GenerateMVPModel {
    private TypeElement parentPresenterElement;
    private GenerateMVPInfo mvpInfo;
    private GenerateNestedMVPModel parent;
    private boolean isRoot;

    public GenerateNestedMVPModel(AnnotatedNestedPresenter nestedPresenter) {
        super(nestedPresenter);
    }

    @Override
    public AnnotatedNestedPresenter getPresenterComponent() {
        return (AnnotatedNestedPresenter) super.getPresenterComponent();
    }

    public GenerateNestedMVPModel getParent() {
        return parent;
    }

    public void setParent(GenerateNestedMVPModel parent) {
        this.parent = parent;
    }

    public TypeElement getParentPresenterElement() {
        return parentPresenterElement;
    }

    public void setParentPresenterElement(TypeElement parentPresenterElement) {
        this.parentPresenterElement = parentPresenterElement;
    }

    public GenerateMVPInfo getMvpInfo() {
        return mvpInfo;
    }

    public void setMvpInfo(GenerateMVPInfo mvpInfo) {
        this.mvpInfo = mvpInfo;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public boolean isRoot() {
        return isRoot;
    }
}
