package ru.vaadinp.place;

import dagger.Lazy;
import ru.vaadinp.security.Gatekeeper;
import ru.vaadinp.vp.api.GateKeeperMVP;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oem on 10/26/16.
 */
public class Place {
	private Map<String, NameToken> nameTokenByEncodedNameToken;
	private NameToken nameToken;

	private final Lazy<? extends GateKeeperMVP> lazyGatekeeper;

	public Place(NameToken... nameTokens) {
		this(null, nameTokens);
	}

	public Place(Lazy<? extends GateKeeperMVP> lazyGatekeeper, NameToken... nameTokens) {

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

	public GateKeeperMVP getGateKeeper() {
		return lazyGatekeeper.get();
	}

	public boolean isSecured() {
		return lazyGatekeeper != null;
	}
}
