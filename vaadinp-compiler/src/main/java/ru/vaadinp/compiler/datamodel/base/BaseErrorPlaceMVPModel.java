package ru.vaadinp.compiler.datamodel.base;

import ru.vaadinp.compiler.datamodel.NestedMVPMetadataModel;
import ru.vaadinp.compiler.datamodel.NestedMVPModel;
import ru.vaadinp.compiler.datamodel.TokenModel;
import ru.vaadinp.place.error.BaseErrorPlace;
import ru.vaadinp.place.error.BaseErrorPlaceTokenSet;

/**
 * Created by oem on 11/7/16.
 */
public class BaseErrorPlaceMVPModel extends NestedMVPModel {

	private static class BaseErrorMVPMetaModel extends NestedMVPMetadataModel {
		public BaseErrorMVPMetaModel() {
			super(BaseErrorPlace.class.getSimpleName());
		}

	}

	public BaseErrorPlaceMVPModel() {
		super(new BaseErrorMVPMetaModel(), BaseErrorPlace.class.getPackage().getName());

		TokenModel tokenModel = new TokenModel(BaseErrorPlaceTokenSet.class.getSimpleName());
		tokenModel.setError(true);
		tokenModel.setDecodedNameToken(BaseErrorPlaceTokenSet.DECODED_VAADINPERROR);
		tokenModel.setEncodedNameToken(BaseErrorPlaceTokenSet.ENCODED_VAADINPERROR);

		addTokenModel(tokenModel);
	}
}
