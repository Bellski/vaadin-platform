package ru.vaadinp.compiler.codegen;

import com.squareup.javapoet.*;
import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.api.MVP;

import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by oem on 11/8/16.
 */
public class MVPImplPoet {
	private final TypeSpec.Builder iml;
	private String name;

	public class DeclarationsBuilder {
		private final TypeSpec.Builder iml;
		private final String mvpName;

		public DeclarationsBuilder(String mvpName) {
			this.iml = TypeSpec
				.interfaceBuilder("Declarations")
				.addAnnotation(Module.class)
				.addModifiers(Modifier.PUBLIC);

			this.mvpName = mvpName;
		}

		public DeclarationsBuilder mvp(ClassName view) {
			iml.addMethod(
				MethodSpec
					.methodBuilder("mvp")
					.addAnnotation(Binds.class)
					.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
					.returns(
						ParameterizedTypeName.get(
							ClassName.get(MVP.class),
							WildcardTypeName.subtypeOf(
								ParameterizedTypeName.get(ClassName.get(PresenterComponent.class), view)
							)
						)
					)
					.addParameter(ClassName.bestGuess(mvpName), "mvp")
					.build()
			);

			return this;
		}

		public DeclarationsBuilder view(ClassName view, ClassName viewImpl) {
			iml.addMethod(
				MethodSpec
					.methodBuilder("view")
					.addAnnotation(Binds.class)
					.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
					.returns(view)
					.addParameter(viewImpl, "view")
					.build()
			);

			return this;
		}

		public DeclarationsBuilder presenter(ClassName presenter, ClassName presenterImpl) {
			iml.addMethod(
				MethodSpec
					.methodBuilder("presenter")
					.addAnnotation(Binds.class)
					.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
					.returns(presenter)
					.addParameter(presenterImpl, "presenter")
					.build()
			);

			return this;
		}

		public MVPImplPoet build() {
			MVPImplPoet.this.iml.addType(iml.build());
			return MVPImplPoet.this;
		}
	}

	public MVPImplPoet(String name) {
		this.name = name;
		iml = impl(name);
	}


	protected TypeSpec.Builder impl(String name) {
		return TypeSpec
			.classBuilder(name)
			.addAnnotation(Singleton.class)
			.addModifiers(Modifier.PUBLIC)
			.superclass(superClass());
	}

	protected Type superClass() {
		return null;
	}


	public DeclarationsBuilder declarations() {
		return new DeclarationsBuilder(name);
	}

	public final MVPImplPoet constructor(ClassName view, ClassName presenter) {
		iml.addMethod(
			constructorBuilder(view, presenter).build()
		);
		return this;
	}

	protected MethodSpec.Builder constructorBuilder(ClassName view, ClassName presenter) {
		return MethodSpec
			.constructorBuilder()
			.addModifiers(Modifier.PUBLIC)
			.addParameters(constructorParams(view, presenter))
			.addCode(CodeBlock.of("super($L);", String.join(",", constructorSuperParams())));
	}

	protected Set<ParameterSpec> constructorParams(ClassName view, ClassName presenter) {
		final Set<ParameterSpec> parameterSpecs = new LinkedHashSet<>();

		parameterSpecs.add(lazyParameter(view, "view"));
		parameterSpecs.add(lazyParameter(presenter, "presenter"));
		parameterSpecs.add(ParameterSpec.builder(RootMVP.class, "rootMVP").build());

		return parameterSpecs;
	}

	protected Set<String> constructorSuperParams() {
		final Set<String> parameters = new LinkedHashSet<>();

		parameters.add("view");
		parameters.add("presenter");
		parameters.add("rootMVP");

		return parameters;
	}


	public TypeSpec build() {
		return iml.build();
	}

	protected ParameterSpec lazyParameter(ClassName type, String name) {
		return ParameterSpec
			.builder(ParameterizedTypeName.get(ClassName.get(Lazy.class), type), name)
			.build();
	}

	public static void main(String[] args) {
		System.out.println(
			JavaFile
				.builder(
					"ru",
					new MVPImplPoet("ApplicationMVP")
						.declarations()
						.mvp(ClassName.bestGuess("ru.Application.View"))
						.view(ClassName.bestGuess("ru.Application.View"), ClassName.bestGuess("ru.ApplicationView"))
						.presenter(ClassName.bestGuess("ru.Application.Presenter"), ClassName.bestGuess("ru.ApplicationPresenter"))
						.build()
						.constructor(ClassName.bestGuess("ru.ApplicationView"), ClassName.bestGuess("ru.ApplicationPresenter"))
						.build()
				)
				.build());
	}
}
