package ru.vaadinp.compiler.datamodel;

/**
 * Created by oem on 11/3/16.
 */
public class TokenModel extends ClassDataModel {
	private String encodedNameToken;
	private String decodedNameToken;
	private String[] parameterNames;
	private int[] parameterIndexes;

	private String encodedNameTokenConstantName;
	private String decodedNameTokenConstantName;
	private String tokenNameConstantName;

	public TokenModel(String name, String packageName) {
		super(name + "Token", packageName);
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

	public String[] getParameterNames() {
		return parameterNames;
	}

	public void setParameterNames(String[] parameterNames) {
		this.parameterNames = parameterNames;
	}

	public int[] getParameterIndexes() {
		return parameterIndexes;
	}

	public void setParameterIndexes(int[] parameterIndexes) {
		this.parameterIndexes = parameterIndexes;
	}

	public String getEncodedNameTokenConstantName() {
		return getName() + "." + encodedNameTokenConstantName;
	}

	public String getDecodedNameTokenConstantName() {
		return getName() + "." + decodedNameTokenConstantName;
	}

	public String getTokenNameConstantName() {
		return getName() + "." + tokenNameConstantName;
	}
}
