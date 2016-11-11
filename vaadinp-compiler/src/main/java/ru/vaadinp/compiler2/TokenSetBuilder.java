package ru.vaadinp.compiler2;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.place.NameToken;

import javax.lang.model.element.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by oem on 11/10/16.
 */
public class TokenSetBuilder implements TypeSpecBuilder, TokenSetRepresentation {

	private final ClassName className;

	public static class Token implements TokenRepresentation {
		private FieldSpec encodedNameTokenField;
		private FieldSpec decodedNameTokenField;
		private FieldSpec tokenField;

		private final String simplifiedNameToken;

		public Token(GenerateNameToken generateNameToken) {
			simplifiedNameToken  = simplifiedNameToken(generateNameToken.nameToken());

			final String[] nameTokenParts = generateNameToken.nameToken().split("/");

			final StringJoiner encodedNameTokenJoiner = new StringJoiner("/");

			final List<String> paramNames = new ArrayList<>();
			final List<String> paramIndexes = new ArrayList<>();

			for (int i = 0; i < nameTokenParts.length; i++) {
				final String nameTokenPart = nameTokenParts[i];

				if (nameTokenPart.charAt(0) == '{') {
					paramNames.add("\"" + nameTokenPart.substring(1, nameTokenPart.length() -1) + "\"");
					paramIndexes.add(String.valueOf(i));

					encodedNameTokenJoiner.add("?");
				} else {
					encodedNameTokenJoiner.add(nameTokenPart);
				}
			}

			encodedNameTokenField = FieldSpec
				.builder(String.class, "ENCODED_" + simplifiedNameToken.toUpperCase())
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
				.initializer("$S", encodedNameTokenJoiner.toString())
				.build();

			decodedNameTokenField = FieldSpec
				.builder(String.class, "DECODED_" + simplifiedNameToken.toUpperCase())
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
				.initializer("$S", generateNameToken.nameToken())
				.build();

			final StringJoiner nameTokenConstructorPatterns = new StringJoiner(", ");
			nameTokenConstructorPatterns.add("$L");
			nameTokenConstructorPatterns.add("$L");

			final List<Object> nameTokenConstructorArguments = new LinkedList<>();
			nameTokenConstructorArguments.add(NameToken.class);
			nameTokenConstructorArguments.add(encodedNameTokenField.name);
			nameTokenConstructorArguments.add(decodedNameTokenField.name);

			if (!paramNames.isEmpty()) {
				nameTokenConstructorPatterns.add("new String[] {" + String.join(",", paramNames) +"}");
				nameTokenConstructorPatterns.add("new int[] {" + String.join(",", paramIndexes) +"}");
			}

			tokenField = FieldSpec
				.builder(NameToken.class, simplifiedNameToken.toUpperCase() + "_TOKEN")
				.addModifiers(Modifier.STATIC, Modifier.FINAL)
				.initializer("new $T(" + nameTokenConstructorPatterns + ")", nameTokenConstructorArguments.toArray())
				.build();
		}

		@Override
		public String getEncodedNameTokenFieldName() {
			return encodedNameTokenField.name;
		}

		@Override
		public String getDecodedNameTokenFieldName() {
			return decodedNameTokenField.name;
		}

		public FieldSpec getTokenField() {
			return tokenField;
		}

		public String getSimplifiedNameToken() {
			return simplifiedNameToken;
		}
	}

	private TypeSpec tokenSetSpec;

	private Map<GenerateNameToken, Token> tokenByAnnotation = new HashMap<>();


	public TokenSetBuilder(GenerateNameToken[] generateNameTokens, ClassName api) {
		className = ClassName.get(api.packageName(), api.simpleName() + "TokenSet");

		final TypeSpec.Builder tokenSetSpecBuilder = TypeSpec
			.classBuilder(className)
			.addModifiers(Modifier.PUBLIC);

		for (GenerateNameToken generateNameToken : generateNameTokens) {
			final Token token = new Token(generateNameToken);
			tokenByAnnotation.put(generateNameToken, token);

			tokenSetSpecBuilder.addField(token.encodedNameTokenField);
			tokenSetSpecBuilder.addField(token.decodedNameTokenField);
			tokenSetSpecBuilder.addField(token.tokenField);
		}

		tokenSetSpec = tokenSetSpecBuilder.build();
	}

	public Token getTokenByAnnotation(GenerateNameToken annotation) {
		return tokenByAnnotation.get(annotation);
	}

	public TypeSpec getTokenSetSpec() {
		return tokenSetSpec;
	}

	public static String simplifiedNameToken(String nameToken) {
		final StringBuilder simplifiedUriBuilder = new StringBuilder();

		final char[] nameTokenChars = nameToken.toCharArray();

		for (int i = 2 ; i < nameTokenChars.length ; i++) {
			char c = nameTokenChars[i];

			if (c == '/') {
				simplifiedUriBuilder.append("_");
			} else if (Character.isAlphabetic(c)) {
				simplifiedUriBuilder.append(c);
			}
		}

		return simplifiedUriBuilder.toString();
	}
	@Override
	public TypeSpec getTypeSpec() {
		return tokenSetSpec;
	}

	@Override
	public ClassName getClassName() {
		return className;
	}

	@Override
	public Collection<TokenRepresentation> getTokenRepresentations() {
		return tokenByAnnotation
			.values()
			.stream()
			.map(token -> (TokenRepresentation) token)
			.collect(Collectors.toList());
	}
}
