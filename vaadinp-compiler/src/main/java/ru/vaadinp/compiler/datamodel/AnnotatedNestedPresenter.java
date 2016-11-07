package ru.vaadinp.compiler.datamodel;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oem on 10/21/16.
 */
public class AnnotatedNestedPresenter extends AnnotatedPresenterComponent {

	private NestedSlotMirror nestedSlotMirror;

	private TokenSetModel tokenSetModel;

	private TokenModel defaultToken;
	private TokenModel notFoundToken;
	private TokenModel errorToken;

	public AnnotatedNestedPresenter(TypeElement presenterElement, ApiMirror apiMirror, ViewMirror viewMirror) {
		super(presenterElement, apiMirror, viewMirror);
	}

	public NestedSlotMirror getNestedSlotMirror() {
		return nestedSlotMirror;
	}

	public void setNestedSlotMirror(NestedSlotMirror nestedSlotMirror) {
		this.nestedSlotMirror = nestedSlotMirror;
	}

	public void setTokenSetModel(TokenSetModel tokenSetModel) {
		this.tokenSetModel = tokenSetModel;
	}

	public TokenSetModel getTokenSetModel() {
		return tokenSetModel;
	}

	public TokenModel getDefaultToken() {
		return defaultToken;
	}

	public void setDefaultToken(TokenModel defaultToken) {
		this.defaultToken = defaultToken;
	}

	public TokenModel getNotFoundToken() {
		return notFoundToken;
	}

	public void setNotFoundToken(TokenModel notFoundToken) {
		this.notFoundToken = notFoundToken;
	}

	public TokenModel getErrorToken() {
		return errorToken;
	}

	public void setErrorToken(TokenModel errorToken) {
		this.errorToken = errorToken;
	}

	public void addTokenModel(TokenModel tokenModel) {
		if (tokenSetModel == null) {
			tokenSetModel = new TokenSetModel(getApiMirror().getName() + "TokenSet", getPackageName());
		}

		tokenModel.setTokenSetModel(tokenSetModel);
		tokenSetModel.add(tokenModel);
	}
}
