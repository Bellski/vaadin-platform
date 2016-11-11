package ru.vaadinp.compiler.datamodel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oem on 11/3/16.
 */
public class TokenModel {
	private String tokenSetName;

	private String encodedNameToken;
	private String decodedNameToken;

	private String simplifiedNameToken;

	private List<String> parameterNames;
	private List<Integer> parameterIndexes;

	private String encodedNameTokenConstantName;
	private String decodedNameTokenConstantName;
	private String tokenNameConstantName;

	private boolean isDefault = false;
	private boolean isError = false;
	private boolean isNotFound = false;

	public TokenModel(String tokenSetName) {
		this.tokenSetName = tokenSetName;
	}

	public String getEncodedNameToken() {
		return encodedNameToken;
	}

	public void setEncodedNameToken(String encodedNameToken) {
		this.encodedNameToken = encodedNameToken;
	}

	public String getDecodedNameToken() {
		return decodedNameToken;
	}

	public void setDecodedNameToken(String decodedNameToken) {
		this.decodedNameToken = decodedNameToken;

		final String constantName = simplifiedNameToken(decodedNameToken).toUpperCase();

		encodedNameTokenConstantName = "ENCODED_" + constantName;

		decodedNameTokenConstantName = "DECODED_" + constantName;

		tokenNameConstantName = constantName + "_TOKEN";

		simplifiedNameToken = constantName.toLowerCase();
	}

	public List<String> getParameterNames() {
		return parameterNames;
	}

	public String joinedParamNames() {
		return getParameterNames()
				.stream()
				.map(paramName -> "\""+ paramName + "\"")
				.collect(Collectors.joining(", "));
	}

	public void setParameterNames(List<String> parameterNames) {
		this.parameterNames = parameterNames;
	}

	public List<Integer> getParameterIndexes() {
		return parameterIndexes;
	}

	public String joinedParamIndexes() {
		return getParameterIndexes()
				.stream()
				.map(String::valueOf)
				.collect(Collectors.joining(", "));
	}

	public void setParameterIndexes(List<Integer> parameterIndexes) {
		this.parameterIndexes = parameterIndexes;
	}

	public String getEncodedNameTokenConstantName() {
		return tokenSetName + "." + encodedNameTokenConstantName;
	}

	public String getEncodedNameTokenFieldName() {
		return encodedNameTokenConstantName;
	}

	public String getDecodedNameTokenFieldName() {
		return decodedNameTokenConstantName;
	}

	public String getDecodedNameTokenConstantName() {
		return tokenSetName + "." + decodedNameTokenConstantName;
	}

	public String getSimplifiedNameToken() {
		return simplifiedNameToken;
	}

	public String getTokenNameConstantName() {
		return tokenSetName + "." + tokenNameConstantName;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean aDefault) {
		isDefault = aDefault;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean error) {
		isError = error;
	}

	public boolean isNotFound() {
		return isNotFound;
	}

	public void setNotFound(boolean notFound) {
		isNotFound = notFound;
	}

	public static String simplifiedNameToken(String nameToken) {
		final StringBuilder simplifiedUriBuilder = new StringBuilder();

		final char[] nameTokenChars = nameToken.toCharArray();

		for (int i = 2 ; i < nameTokenChars.length ; i++) {
			char c = nameTokenChars[i];

			if (c == '/') {
				simplifiedUriBuilder.append("_");
			} else if (Character.isAlphabetic(c)) {
				simplifiedUriBuilder.append(c);
			}
		}

		return simplifiedUriBuilder.toString();
	}
}
