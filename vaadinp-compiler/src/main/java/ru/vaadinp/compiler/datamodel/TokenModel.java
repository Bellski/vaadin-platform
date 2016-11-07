package ru.vaadinp.compiler.datamodel;

import java.util.List;

/**
 * Created by oem on 11/3/16.
 */
public class TokenModel  {
	private TokenSetModel tokenSetModel;

	private String encodedNameToken;
	private String decodedNameToken;
	private List<String> parameterNames;
	private List<Integer> parameterIndexes;

	private String encodedNameTokenConstantName;
	private String decodedNameTokenConstantName;
	private String tokenNameConstantName;

	public void setTokenSetModel(TokenSetModel tokenSetModel) {
		this.tokenSetModel = tokenSetModel;
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
	}

	public List<String> getParameterNames() {
		return parameterNames;
	}

	public void setParameterNames(List<String> parameterNames) {
		this.parameterNames = parameterNames;
	}

	public List<Integer> getParameterIndexes() {
		return parameterIndexes;
	}

	public void setParameterIndexes(List<Integer> parameterIndexes) {
		this.parameterIndexes = parameterIndexes;
	}

	public String getEncodedNameTokenConstantName() {
		return tokenSetModel.getName() + "." + encodedNameTokenConstantName;
	}

	public String getDecodedNameTokenConstantName() {
		return tokenSetModel.getName() + "." + decodedNameTokenConstantName;
	}

	public String getTokenNameConstantName() {
		return tokenSetModel.getName() + "." + tokenNameConstantName;
	}
}
