package ru.vaadinp.place.error;

import com.vaadin.ui.Label;
import ru.vaadinp.vp.ViewImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oem on 10/10/16.
 */
@Singleton
public class BaseErrorPlaceView extends ViewImpl<BaseErrorPlace.Presenter> implements BaseErrorPlace.View {

	@Inject
	public BaseErrorPlaceView() {
		initComponent(new Label("ERROR"));
	}
}
