package ru.vaadinp.compiler.datamodel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.vaadinp.annotations.dagger.DefaultPlaceNameToken;
import ru.vaadinp.annotations.dagger.ErrorPlaceNameToken;
import ru.vaadinp.annotations.dagger.NameTokenParts;
import ru.vaadinp.annotations.dagger.NotFoundPlaceNameToken;
import ru.vaadinp.error.ErrorManager;
import ru.vaadinp.place.PlaceManager;
import ru.vaadinp.place.PlaceUtils;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.uri.UriFragmentSource;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by oem on 11/7/16.
 */
public class ApplicationModuleModel extends ClassDataModel {
	private List<MVPModel<?>> mvpModels;

	private NestedMVPModel defaultMVPModel;
	private NestedMVPModel errorMVPModel;
	private NestedMVPModel notFoundMVPModel;

	private ClassDataModel placeManagerModel;
	private ClassDataModel errorManagerModel;
	private ClassDataModel uriFragmentSourceModel;

	private List<TokenModel> tokenModels = new ArrayList<>();

	public ApplicationModuleModel(String name, String packageName) {
		super(name + "Module", packageName);

		importClass(Module.class);
		importClass(Binds.class);
		importClass(RootMVP.class);
		importClass(Provides.class);
		importClass(Singleton.class);
		importClass(NameTokenParts.class);
		importClass(Set.class);
		importClass(HashSet.class);
		importClass(DefaultPlaceNameToken.class);
		importClass(NotFoundPlaceNameToken.class);
		importClass(ErrorPlaceNameToken.class);
		importClass(PlaceManager.class);
		importClass(ErrorManager.class);
		importClass(UriFragmentSource.class);
		importClass(PlaceUtils.class);
	}

	public void setMvpModels(List<MVPModel<?>> mvpModels) {
		this.mvpModels = mvpModels;
		mvpModels.forEach((mvpModel -> {
			importClassDataModel(mvpModel);

			if (mvpModel instanceof NestedMVPModel) {
				if (((NestedMVPModel) mvpModel).hasTokenSetModel()) {
					importClassDataModel(((NestedMVPModel) mvpModel).getTokenSetModel());
//					importClassDataModel(((NestedMVPModel) mvpModel).getTokenSetModel().getGatekeeper());
					tokenModels.addAll(((NestedMVPModel) mvpModel).getTokenModels());
				}
			}
		}));
	}

	public List<MVPModel<?>> getMvpModels() {
		return mvpModels;
	}

	public List<String> getMVPDeclarations() {
		return mvpModels
			.stream()
			.map(mvpModel -> mvpModel.getName() + ".Declarations.class")
			.collect(Collectors.toList());
	}

	public NestedMVPModel getDefaultMVPModel() {
		return defaultMVPModel;
	}

	public void setDefaultMVPModel(NestedMVPModel defaultMVPModel) {
		this.defaultMVPModel = defaultMVPModel;
	}

	public NestedMVPModel getErrorMVPModel() {
		return errorMVPModel;
	}

	public void setErrorMVPModel(NestedMVPModel errorMVPModel) {
		this.errorMVPModel = errorMVPModel;
	}

	public NestedMVPModel getNotFoundMVPModel() {
		return notFoundMVPModel;
	}

	public void setNotFoundMVPModel(NestedMVPModel notFoundMVPModel) {
		this.notFoundMVPModel = notFoundMVPModel;
	}

	public ClassDataModel getPlaceManagerModel() {
		return placeManagerModel;
	}

	public void setPlaceManagerModel(ClassDataModel placeManagerModel) {
		this.placeManagerModel = placeManagerModel;
		importClassDataModel(placeManagerModel);
	}

	public ClassDataModel getErrorManagerModel() {
		return errorManagerModel;
	}

	public void setErrorManagerModel(ClassDataModel errorManagerModel) {
		this.errorManagerModel = errorManagerModel;
		importClassDataModel(errorManagerModel);
	}

	public ClassDataModel getUriFragmentSourceModel() {
		return uriFragmentSourceModel;
	}

	public void setUriFragmentSourceModel(ClassDataModel uriFragmentSourceModel) {
		this.uriFragmentSourceModel = uriFragmentSourceModel;
		importClassDataModel(uriFragmentSourceModel);
	}

	public List<TokenModel> getTokenModels() {
		return tokenModels;
	}
}
