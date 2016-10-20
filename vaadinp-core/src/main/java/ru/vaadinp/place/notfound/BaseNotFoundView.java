package ru.vaadinp.place.notfound;

import ru.vaadinp.vp.ViewImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/10/16.
 */
@Singleton
public class BaseNotFoundView extends ViewImpl<BaseNotFound.Presenter> implements BaseNotFound.View {

	@Inject
	public BaseNotFoundView() {
	}
}
