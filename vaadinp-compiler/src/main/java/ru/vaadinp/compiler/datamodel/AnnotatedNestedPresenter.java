package ru.vaadinp.compiler.datamodel;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oem on 10/21/16.
 */
public class AnnotatedNestedPresenter extends AnnotatedPresenterComponent {

	private NestedSlotMirror nestedSlotMirror;
	private List<TokenModel> tokenModelList = new ArrayList<>();

	private TokenModel tokenModel;

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

	public void addTokenModel(TokenModel tokenModel) {
		tokenModelList.add(tokenModel);
	}

	public List<TokenModel> getTokenModelList() {
		return tokenModelList;
	}

	public TokenModel getTokenModel() {
		return tokenModel;
	}

	public void setTokenModel(TokenModel tokenModel) {
		this.tokenModel = tokenModel;
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
}
