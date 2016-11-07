package ru.vaadinp.compiler2.datamodel;

import java.util.List;
import java.util.stream.Collector;
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

		final String constantName = decodedNameToken
			.replace("!/", "")
			.replaceAll("[^A-Za-z0-9]", "_")
			.trim()
			.toUpperCase();

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
}
