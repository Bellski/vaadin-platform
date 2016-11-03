package ru.vaadinp.vp.api;

import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.place.Place;

/**
 * Created by oem on 10/27/16.
 */
public interface PlaceMVP<P extends BaseNestedPresenter<?>> extends NestedMVP<P> {
	Place getPlace();
}
