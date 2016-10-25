package ru.vaadinp.place.notfound;

import com.vaadin.ui.Label;
import ru.vaadinp.vp.ViewImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/10/16.
 */
@Singleton
public class BaseNotFoundPlaceView extends ViewImpl<BaseNotFoundPlace.Presenter> implements BaseNotFoundPlace.View {

	@Inject
	public BaseNotFoundPlaceView() {
		initComponent(new Label("NOT FOUND"));
	}
}
