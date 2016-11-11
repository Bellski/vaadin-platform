package ru.vaadinp.compiler2;

import com.squareup.javapoet.*;
import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.annotations.GenerateMVPInfo;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.MVPImpl;
import ru.vaadinp.vp.MVPInfo;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.api.MVP;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.*;

/**
 * Created by oem on 11/9/16.
 */
public class MVPBuilder extends PresenterScanner implements TypeSpecBuilder {
	private final ClassName mvpClassName;

	protected final List<String> constructorSuperParameterPatterns = new LinkedList<>(); {
		constructorSuperParameterPatterns.add("$L");
		constructorSuperParameterPatterns.add("$L");
		constructorSuperParameterPatterns.add("$L");
	}

	protected final List<Object> constructorSuperParameterArguments = new LinkedList<>(); {
		constructorSuperParameterArguments.add("view");
		constructorSuperParameterArguments.add("presenter");
		constructorSuperParameterArguments.add("rootMVP");
	}

	protected final TypeSpec.Builder declarationsBuilder;

	protected final Set<ParameterSpec> constructorParameters = new LinkedHashSet<>(); {
		constructorParameters.add(lazyParameter(viewImpl, "view"));
		constructorParameters.add(lazyParameter(presenterImpl, "presenter"));
		constructorParameters.add(ParameterSpec.builder(RootMVP.class, "rootMVP").build());
	}

	public MVPBuilder(Elements elementUtils, Types typeUtils, TypeElement presenterElement) {
		super(elementUtils, typeUtils, presenterElement);

		mvpClassName = ClassName.get(packageName, api.simpleName() + "MVP");

		declarationsBuilder = TypeSpec
			.interfaceBuilder("Declarations")
			.addModifiers(Modifier.PUBLIC)
			.addAnnotation(Module.class)
			.addMethod(mvp())
			.addMethod(view())
			.addMethod(presenter());
	}


	protected ClassName getSuperClass() {
		return ClassName.get(MVPImpl.class);
	}


	protected MethodSpec mvp() {
		return MethodSpec
			.methodBuilder("mvp")
			.addAnnotation(Binds.class)
			.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
			.returns(
				ParameterizedTypeName.get(
					getMVPApi(),
					WildcardTypeName.subtypeOf(
						ParameterizedTypeName.get(getMVPWildCardPresenter(), viewApi)
					)
				)
			)
			.addParameter(mvpClassName, "mvp")
			.build();
	}

	protected MethodSpec view() {
		return MethodSpec
			.methodBuilder("view")
			.addAnnotation(Binds.class)
			.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
			.returns(viewApi)
			.addParameter(viewImpl, "view")
			.build();
	}

	protected MethodSpec presenter() {
		return MethodSpec
			.methodBuilder("presenter")
			.addAnnotation(Binds.class)
			.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
			.returns(presenterApi)
			.addParameter(presenterImpl, "presenter")
			.build();
	}

	protected ClassName getMVPApi() {
		return ClassName.get(MVP.class);
	}

	protected ClassName getMVPWildCardPresenter() {
		return ClassName.get(PresenterComponent.class);
	}

	protected ParameterSpec lazyParameter(ClassName type, String name) {
		return ParameterSpec
			.builder(ParameterizedTypeName.get(ClassName.get(Lazy.class), type), name)
			.build();
	}

	@Override
	protected void processGenerateMVPInfo(GenerateMVPInfo generateMVPInfo) {
		constructorSuperParameterArguments.add(MVPInfo.class);

		final StringJoiner mvpInfoParameterPatterns = new StringJoiner(", ");

		if (!generateMVPInfo.caption().isEmpty()) {
			mvpInfoParameterPatterns.add("$S");
			constructorSuperParameterArguments.add(generateMVPInfo.caption());
		}

		if (!generateMVPInfo.title().isEmpty()) {
			mvpInfoParameterPatterns.add("$S");
			constructorSuperParameterArguments.add(generateMVPInfo.title());
		}

		if (!generateMVPInfo.historyToken().isEmpty()) {
			mvpInfoParameterPatterns.add("$S");
			constructorSuperParameterArguments.add(generateMVPInfo.historyToken());
		}

		if (generateMVPInfo.priority() > -1) {
			mvpInfoParameterPatterns.add("$L");
			constructorSuperParameterArguments.add(generateMVPInfo.priority());
		}

		constructorSuperParameterPatterns.add("new $T(" + mvpInfoParameterPatterns.toString() + ")");

	}


	@Override
	public TypeSpec getTypeSpec() {
		return TypeSpec
			.classBuilder(mvpClassName)
			.addModifiers(Modifier.PUBLIC)
			.addAnnotation(Singleton.class)
			.superclass(ParameterizedTypeName.get(getSuperClass(), ClassName.get(presenterElement)))
			.addMethod(
				MethodSpec
					.constructorBuilder()
					.addModifiers(Modifier.PUBLIC)
					.addAnnotation(Inject.class)
					.addParameters(constructorParameters)
					.addCode("super(" + String.join(", ", constructorSuperParameterPatterns
					) + "); \n", constructorSuperParameterArguments.toArray())
					.build()
			)
			.addType(declarationsBuilder.build())
			.build();
	}

	@Override
	public ClassName getClassName() {
		return mvpClassName;
	}
}
