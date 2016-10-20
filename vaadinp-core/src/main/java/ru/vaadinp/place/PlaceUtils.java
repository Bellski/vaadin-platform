package ru.vaadinp.place;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by oem on 10/11/16.
 */
public class PlaceUtils {
	public static Set<String> breakIntoNameTokenParts(String namePlace) {
		final Set<String> nameTokenParts = new HashSet<>();

		for (String namePlacePart : namePlace.split("/")) {
			if (!namePlacePart.startsWith("{")) {
				nameTokenParts.add(namePlacePart);
			}
		}
		return nameTokenParts;
	}
}
