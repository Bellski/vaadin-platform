package ru.vaadinp.place;

import dagger.Lazy;
import ru.vaadinp.security.Gatekeeper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oem on 10/26/16.
 */
public class Place {
	private Map<String, NameToken> nameTokenByEncodedNameToken;
	private NameToken nameToken;

	private final Lazy<Gatekeeper> lazyGatekeeper;

	public Place(NameToken... nameTokens) {
		this(null, nameTokens);
	}

	public Place(Lazy<Gatekeeper> lazyGatekeeper, NameToken... nameTokens) {

		if (nameTokens.length == 1) {
			nameToken = nameTokens[0];
		} else {
			nameTokenByEncodedNameToken = new HashMap<>();

			for (NameToken token : nameTokens) {
				nameTokenByEncodedNameToken.put(token.getEncodedNameToken(), token);
			}
		}

		this.lazyGatekeeper = lazyGatekeeper;
	}

	public NameToken getNameToken(String encodedNameToken) {
		if (nameTokenByEncodedNameToken == null) {
			return nameToken;
		} else {
			return nameTokenByEncodedNameToken.get(encodedNameToken);
		}
	}

	public Lazy<Gatekeeper> getLazyGatekeeper() {
		return lazyGatekeeper;
	}

	public boolean isSecured() {
		return lazyGatekeeper != null;
	}
}
