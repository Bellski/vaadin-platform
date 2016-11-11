package ru.vaadinp.compiler2;

import com.squareup.javapoet.*;
import dagger.Binds;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.place.Place;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.api.NestedMVP;
import ru.vaadinp.vp.api.PlaceMVP;

import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.StringJoiner;

/**
 * Created by oem on 11/9/16.
 */
public class NestedMVPBuilder extends MVPBuilder {

	private TokenSetBuilder tokenSetBuilder;

	public NestedMVPBuilder(Elements elementUtils, Types typeUtils, TypeElement presenterElement) {
		super(elementUtils, typeUtils, presenterElement);
		scan();
	}

	@Override
	protected ClassName getSuperClass() {
		return ClassName.get(NestedMVPImpl.class);
	}

	@Override
	protected ClassName getMVPApi() {
		return ClassName.get(NestedMVP.class);
	}

	@Override
	protected ClassName getMVPWildCardPresenter() {
		return ClassName.get(BaseNestedPresenter.class);
	}

	@Override
	protected void processRevealIn(ClassName parentMVP) {
		constructorParameters.add(ParameterSpec.builder(parentMVP, "parent").build());
		constructorSuperParameterPatterns.add("$L");
		constructorSuperParameterArguments.add("parent");
	}

	@Override
	protected void processNestedSlot(String nestedSlotElement) {
		declarationsBuilder.addMethod (
			MethodSpec
				.methodBuilder("nestedSlot")
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.addAnnotation(Provides.class)
				.addAnnotation(Singleton.class)
				.addAnnotation(
					AnnotationSpec
						.builder(RevealIn.class)
						.addMember("value", "$L.class", presenterImpl.simpleName())
						.build()
				)
				.returns(NestedSlot.class)
				.addCode(
					CodeBlock.of("return $L.$L; \n", presenterImpl.simpleName(), nestedSlotElement)
				)
				.build()
		);
	}


	@Override
	protected void processGenerateNameTokens(GenerateNameToken[] generateNameTokens) {
		tokenSetBuilder = new TokenSetBuilder(generateNameTokens, api);

		final StringJoiner placeParameterPatterns = new StringJoiner(", ");

		constructorSuperParameterArguments.add(Place.class);

		for (GenerateNameToken generateNameToken : generateNameTokens) {
			declarationsBuilder
				.addMethod(
					MethodSpec
						.methodBuilder(
							tokenSetBuilder.getTokenByAnnotation(generateNameToken).getSimplifiedNameToken()
						)
						.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
						.addAnnotation(IntoMap.class)
						.addAnnotation(PlacesMap.class)
						.addAnnotation(Binds.class)
						.addAnnotation(
							AnnotationSpec
								.builder(StringKey.class)
								.addMember(
									"value",
									"$T.$L",
									ClassName.bestGuess(tokenSetBuilder.getTokenSetSpec().name),
									tokenSetBuilder.getTokenByAnnotation(generateNameToken).getEncodedNameTokenFieldName()

								)
								.build()
						)
						.returns(
							ParameterizedTypeName.get(ClassName.get(PlaceMVP.class), WildcardTypeName.subtypeOf(TypeName.OBJECT))
						)
						.addParameter(ClassName.bestGuess(api.simpleName() + "MVP"), "mvp")
						.build()
				);

			placeParameterPatterns.add("$T.$L");
			constructorSuperParameterArguments.add(ClassName.bestGuess(tokenSetBuilder.getTokenSetSpec().name));
			constructorSuperParameterArguments.add(tokenSetBuilder.getTokenByAnnotation(generateNameToken).getTokenField().name);
		}

		constructorSuperParameterPatterns.add("new $T(" + placeParameterPatterns + ")");

	}

	public TokenSetBuilder getTokenSetBuilder() {
		return tokenSetBuilder;
	}

}
