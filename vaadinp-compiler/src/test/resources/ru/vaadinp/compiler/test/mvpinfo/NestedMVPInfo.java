package ru.vaadinp.compiler.test.mvpinfo;

/**
 * Created by oem on 10/18/16.
 */
public interface NestedMVPInfo {
	interface View extends ru.vaadinp.vp.View {}
	interface Presenter extends ru.vaadinp.vp.api.Presenter {}
}
