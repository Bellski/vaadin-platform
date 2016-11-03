package ru.vaadinp.place;

/**
 * Created by oem on 11/3/16.
 */
public class NameToken {
	private final String encodedNameToken;
	private final String decodedNameToken;
	private final String[] parameterNames;
	private final int[] parameterIndexes;

	public NameToken(String encodedNameToken, String decodedNameToken) {
		this(encodedNameToken, decodedNameToken, new String[] {}, new int[] {});
	}

	public NameToken(String encodedNameToken, String decodedNameToken, String[] parameterNames, int[] parameterIndexes) {
		this.encodedNameToken = encodedNameToken;
		this.decodedNameToken = decodedNameToken;
		this.parameterNames = parameterNames;
		this.parameterIndexes = parameterIndexes;
	}

	public String getEncodedNameToken() {
		return encodedNameToken;
	}

	public String getDecodedNameToken() {
		return decodedNameToken;
	}

	public String[] getParameterNames() {
		return parameterNames;
	}

	public int[] getParameterIndexes() {
		return parameterIndexes;
	}

	public boolean hasParameters() {
		return parameterNames != null || parameterIndexes.length > 0;
	}
}
