package ru.vaadinp.place.error;

import ru.vaadinp.vp.ViewImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/10/16.
 */
@Singleton
public class BaseErrorView extends ViewImpl<BaseError.Presenter> implements BaseError.View {

	@Inject
	public BaseErrorView() {
	}
}
