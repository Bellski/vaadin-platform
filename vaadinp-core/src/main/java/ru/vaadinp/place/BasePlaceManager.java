package ru.vaadinp.place;

import ru.vaadinp.annotations.dagger.DefaultPlaceNameToken;
import ru.vaadinp.annotations.dagger.NameTokenParts;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.error.ErrorManager;
import ru.vaadinp.uri.UriFragmentSource;
import ru.vaadinp.uri.handlers.UriFragmentChangeHandler;
import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.PlaceVPComponent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author bellski
 *
 *
 */
@Singleton
public class BasePlaceManager implements PlaceManager, UriFragmentChangeHandler {
	private final Map<String, PlaceVPComponent<?, ?>> placeByNameToken;
	private final UriFragmentSource uriFragmentSource;
	private final ErrorManager errorManager;
	private final Set<String> nameTokenParts;

	private List<PlaceRequest> placeHierarchy = new ArrayList<>();

	private final PlaceRequest defaultPlaceRequest;

	private String currentHistoryToken = "";

	@Inject
	public BasePlaceManager(@PlacesMap Map<String, PlaceVPComponent<?, ?>> placeByNameToken,
							@NameTokenParts Set<String> nameTokenParts,
							UriFragmentSource uriFragmentSource,
							ErrorManager errorManager,
							@DefaultPlaceNameToken String defaultTokenName
	) {
		this.placeByNameToken = placeByNameToken;
		this.nameTokenParts = nameTokenParts;
		this.uriFragmentSource = uriFragmentSource;
		this.errorManager = errorManager;
		this.defaultPlaceRequest = new PlaceRequest
			.Builder()
			.nameToken(defaultTokenName)
			.build();

		this.errorManager.setPlaceManager(this);
		this.uriFragmentSource.setUriFragmentChangeHandler(this);
	}

	@Override
	public void onUriFragmentChanged(String uriFragment) {
		try {
			handleUriFragmentChange(uriFragment);
		} catch (UnsupportedEncodingException e) {
			errorManager.exception(uriFragment, e);
		}
	}

	private void handleUriFragmentChange(String uriFragment) throws UnsupportedEncodingException {
		if (uriFragmentIsEmpty(uriFragment)) {
			revealDefaultPlace();
		} else {
			final PlaceVPComponent<?, ?> place = placeByNameToken.get(toPlacePattern(uriFragment));

			if (place == null) {
				errorManager.placeNotFound(uriFragment);
			} else {
				placeHierarchy = toPlaceRequestHierarchy(uriFragment, place);
				doRevealPlace(getCurrentPlaceRequest(), place, true);
			}
		}
	}

	private boolean uriFragmentIsEmpty(String uriFragment) {
		return uriFragment == null || uriFragment.isEmpty() || uriFragment.equals("!/");
	}

	private List<PlaceRequest> toPlaceRequestHierarchy(String historyToken, PlaceVPComponent<?, ?> place) {
		List<PlaceRequest> result = new ArrayList<>(); //TODO: надо будет потом упразднить.
		result.add(toPlaceRequest(historyToken, place));

		return result;
	}

	private PlaceRequest toPlaceRequest(String uriFragment, PlaceVPComponent<?, ?> place) {
		int split = uriFragment.indexOf('?');

		String query = (split != -1) ? uriFragment.substring(split + 1) : ""; //TODO: добавить поддержку параметров через вопросик

		final String[] placeParts = uriFragment.split("/");

		Map<String, String> params = null;

		if (place.hasParameters()) {
			params = new HashMap<>(3);

			for (int i = 0; i < place.getParameterIndexes().length; i++) {
				params.put(place.getParameterNames()[i], placeParts[place.getParameterIndexes()[i]]);
			}
		}

		return new PlaceRequest
			.Builder()
			.nameToken(place.toString())
			.with(params)
			.build();
	}

	protected void doRevealPlace(PlaceRequest request, PlaceVPComponent<?, ?> place, boolean updateBrowser) throws UnsupportedEncodingException {
		if (place.isSecured()) {
			place
				.getLazyGatekeeper()
				.get()
				.canReveal(place);
		} else {
			final PlaceRequest originalRequest = getCurrentPlaceRequest();

			final NestedPresenter<?> presenter = place.getPresenter();

			presenter.prepareFromRequest(request);

			if (originalRequest == getCurrentPlaceRequest()) {
				updateHistory(request, updateBrowser);
			}

			if (!presenter.useManualReveal()) {
				if (!presenter.isVisible()) {
					presenter.forceReveal();
				}
			}
		}

	}


	private void updateHistory(PlaceRequest request, boolean updateBrowserUrl) throws UnsupportedEncodingException {
		if (!request.hasSameNameToken(getCurrentPlaceRequest())) {
			throw new IllegalStateException("Internal error, PlaceRequest passed to" +
				"updateHistory doesn't match the tail of the place hierarchy.");
		}

		placeHierarchy.set(placeHierarchy.size() - 1, request);

		if (updateBrowserUrl) {
			final String historyToken = toHistoryToken(placeHierarchy);
			final String browserHistoryToken = uriFragmentSource.getCurrentUriFragment();

			if (!browserHistoryToken.equals(historyToken)) {
				uriFragmentSource.setCurrentUriFragment(historyToken, false);
			}
			saveHistoryToken(historyToken);
		}
	}

	private void saveHistoryToken(String historyToken) {
		currentHistoryToken = historyToken;
	}

	private String toHistoryToken(List<PlaceRequest> placeHierarchy) throws UnsupportedEncodingException {
		if (placeHierarchy.size() != 1) {
			throw new IllegalStateException("Expected a place hierarchy with exactly one place.");
		}

		return toPlaceToken(placeHierarchy.get(0));
	}

	private String toPlaceToken(PlaceRequest placeRequest) throws UnsupportedEncodingException {
		String placeToken = placeRequest.getNameToken();
		StringBuilder queryStringBuilder = new StringBuilder();
		String querySeparator = "";

		for (String parameterName : placeRequest.getParameterNames()) {
			String parameterValue = placeRequest.getParameter(parameterName, null);
			if (parameterValue != null) {
				String encodedParameterValue = URLDecoder.decode(parameterValue, "UTF-8");

				if (placeToken.contains("/{" + parameterName + "}")) {
					// route parameter
					placeToken = placeToken.replace("{" + parameterName + "}", encodedParameterValue);
				} else {
					// query parameter
					queryStringBuilder.append(querySeparator).append(parameterName).append("=")
						.append(encodedParameterValue);
					querySeparator = "&";
				}
			}
		}

		String queryString = queryStringBuilder.toString();
		if (!queryString.isEmpty()) {
			placeToken = placeToken + "?" + queryString;
		}

		return placeToken;
	}

	@Override
	public PlaceRequest getCurrentPlaceRequest() {
		if (placeHierarchy.size() > 0) {
			return placeHierarchy.get(placeHierarchy.size() - 1);
		} else {
			return new PlaceRequest.Builder().build();
		}
	}

	@Override
	public void revealPlace(PlaceRequest request) {
		revealPlace(request, false);
	}

	@Override
	public void revealPlace(PlaceRequest request, boolean updateBrowserUrl) {
		placeHierarchy.clear();
		placeHierarchy.add(request);

		final PlaceVPComponent<?, ?> place = placeByNameToken.get(toPlacePattern(request.getNameToken()));

		if (place == null) {
			errorManager.placeNotFound(request.getNameToken());
		} else {
			try {
				doRevealPlace(request, place, updateBrowserUrl);
			} catch (UnsupportedEncodingException e) {
				errorManager.exception(request, e);
			}
		}
	}

	private String toPlacePattern(String nameToken) {
		final StringJoiner placeStringJoiner = new StringJoiner("/");

		for (String part : nameToken.split("/")) {
			if (nameTokenParts.contains(part)) {
				placeStringJoiner.add(part);
			} else {
				placeStringJoiner.add("?");
			}
		}

		return placeStringJoiner.toString();
	}

	@Override
	public void revealDefaultPlace() {
		revealPlace(getDefaultPlaceRequest(), false);
	}

	@Override
	public void revealCurrentPlace() {
		try {
			handleUriFragmentChange(uriFragmentSource.getCurrentUriFragment());
		} catch (UnsupportedEncodingException e) {
			errorManager.exception(uriFragmentSource.getCurrentUriFragment(), e);
		}
	}

	@Override
	public PlaceRequest getDefaultPlaceRequest() {
		return defaultPlaceRequest;
	}

	@Override
	public void clean() {
		uriFragmentSource.clean();
		placeByNameToken.clear();
		placeHierarchy.clear();
	}
}
