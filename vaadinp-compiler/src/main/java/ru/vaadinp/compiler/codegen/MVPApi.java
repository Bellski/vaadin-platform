package ru.vaadinp.compiler.codegen;

import com.squareup.javapoet.ClassName;

/**
 * Created by oem on 11/8/16.
 */
public class MVPApi {
	private ClassName apiClass;
	private ClassName viewClass;
	private ClassName presenterClass;

	public MVPApi(ClassName apiClass) {
		this.apiClass = apiClass;
	}

	public ClassName getApiClass() {
		return apiClass;
	}

	public ClassName getViewClass() {
		return viewClass;
	}

	public ClassName getPresenterClass() {
		return presenterClass;
	}
}
