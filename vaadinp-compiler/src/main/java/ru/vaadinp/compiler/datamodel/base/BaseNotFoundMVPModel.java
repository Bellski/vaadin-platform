package ru.vaadinp.compiler.datamodel.base;

import ru.vaadinp.compiler.datamodel.NestedMVPMetadataModel;
import ru.vaadinp.compiler.datamodel.NestedMVPModel;
import ru.vaadinp.compiler.datamodel.TokenModel;
import ru.vaadinp.place.notfound.BaseNotFoundPlace;
import ru.vaadinp.place.notfound.BaseNotFoundPlaceTokenSet;

/**
 * Created by oem on 11/7/16.
 */
public class BaseNotFoundMVPModel extends NestedMVPModel {

	private static class BaseNotFoundMVPMetadataModel extends NestedMVPMetadataModel {
		public BaseNotFoundMVPMetadataModel() {
			super(BaseNotFoundPlace.class.getSimpleName());
		}

	}

	public BaseNotFoundMVPModel() {
		super(new BaseNotFoundMVPMetadataModel(), BaseNotFoundPlace.class.getPackage().getName());

		TokenModel tokenModel = new TokenModel(BaseNotFoundPlaceTokenSet.class.getSimpleName());
		tokenModel.setNotFound(true);
		tokenModel.setDefault(true);
		tokenModel.setDecodedNameToken(BaseNotFoundPlaceTokenSet.DECODED_VAADINPNOTFOUND);
		tokenModel.setEncodedNameToken(BaseNotFoundPlaceTokenSet.ENCODED_VAADINPNOTFOUND);

		addTokenModel(tokenModel);
	}
}
