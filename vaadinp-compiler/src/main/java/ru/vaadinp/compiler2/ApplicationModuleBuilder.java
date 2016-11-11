package ru.vaadinp.compiler2;

import com.squareup.javapoet.*;
import dagger.Module;
import ru.vaadinp.place.PlaceUtils;

import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by oem on 11/11/16.
 */
public class ApplicationModuleBuilder implements TypeSpecBuilder {
	private final ClassName className;

	private final TypeSpec.Builder implBuilder;
	private final TypeSpec.Builder systemDeclarationsBudiler;
	private final TypeSpec.Builder mvpDeclarationsBudiler;

	private final List<String> mvpDeclarationsPatterns = new LinkedList<>();
	private final List<ClassName> mvpDeclarations = new LinkedList<>();

	private final List<String> tokenPartPatterns = new LinkedList<>();
	private final List<Object> tokenPartArguments = new LinkedList<>();

	private final FieldSpec.Builder tokenPartsFieldBuilder;

	public ApplicationModuleBuilder(String rootApiName, String packageName) {
		this.className = ClassName.get(packageName, rootApiName + "ApplicationModule");

		systemDeclarationsBudiler = TypeSpec
			.interfaceBuilder("SystemDeclarations")
			.addAnnotation(Module.class);

		mvpDeclarationsBudiler = TypeSpec.interfaceBuilder("MVPDeclarations");

		implBuilder = TypeSpec
			.classBuilder(className)
			.addAnnotation(
				AnnotationSpec
					.builder(Module.class)
					.addMember("includes", "{\n" + className.simpleName() + ".SystemDeclarations.class,\n" + className.simpleName() + ".MVPDeclarations.class\n" + "}")
					.build()
			)
			.addModifiers(Modifier.PUBLIC);

		tokenPartsFieldBuilder = FieldSpec
			.builder(ParameterizedTypeName.get(Set.class, String.class), "TOKEN_PARTS")
			.initializer("new $T()", HashSet.class)
			.addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

	}

	public TypeSpec getTypeSpec() {
		return implBuilder
			.addType(systemDeclarationsBudiler.build())
			.addType(
				mvpDeclarationsBudiler
					.addAnnotation(
						AnnotationSpec
							.builder(Module.class)
							.addMember(
								"includes",
								"{\n" + String.join(",\n", mvpDeclarationsPatterns) + "\n}",
								mvpDeclarations.toArray()
							)
							.build()
					)
					.build()
			)
			.addField(
				tokenPartsFieldBuilder
					.build()
			)
			.addStaticBlock(CodeBlock.of(String.join("",tokenPartPatterns), tokenPartArguments.toArray()))
			.build();
	}

	@Override
	public ClassName getClassName() {
		return className;
	}

	public void addDeclaration(ClassName declaration) {
		mvpDeclarationsPatterns.add("$T.Declarations.class");
		mvpDeclarations.add(declaration);
	}

	public void addTokenSet(TokenSetRepresentation tokenSetRepresentation) {
		for (TokenRepresentation token : tokenSetRepresentation.getTokenRepresentations()) {
			tokenPartArguments.add(PlaceUtils.class);
			tokenPartArguments.add(tokenSetRepresentation.getClassName());
			tokenPartArguments.add(token.getDecodedNameTokenFieldName());
			tokenPartPatterns.add("TOKEN_PARTS.addAll($T.breakIntoNameTokenParts($T.$L)); \n");
		}
	}

}
