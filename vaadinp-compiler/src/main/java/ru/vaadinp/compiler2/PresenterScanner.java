package ru.vaadinp.compiler2;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.sun.tools.javac.code.Symbol;
import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.annotations.GenerateMVPInfo;
import ru.vaadinp.annotations.GenerateNameToken;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.ElementScanner8;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by Aleksandr on 05.11.2016.
 */
public class PresenterScanner {
	protected final Elements elementUtils;
	protected final Types typeUtils;
	protected final TypeElement presenterElement;

	protected final String packageName;

	protected final ClassName api;

	protected final ClassName viewApi;
	protected final ClassName viewImpl;

	protected final ClassName presenterApi;
	protected final ClassName presenterImpl;

	private class InnerScanner extends ElementScanner8<Void, Void> {
		@Override
		public Void visitVariable(VariableElement e, Void aVoid) {

			switch (e.getKind()) {
				case FIELD:
					if (e.asType().toString().equals(NestedSlot.class.getName())) {
						processNestedSlot(e.getSimpleName().toString());
					}
					break;
				case PARAMETER:
					final RevealIn revealInAnnotation = e.getAnnotation(RevealIn.class);

					if (revealInAnnotation != null) {
						try {
							revealInAnnotation.value();
						} catch (MirroredTypeException mte) {
							final Element parentPresenterElement = typeUtils.asElement(mte.getTypeMirror());

							processRevealIn(
								ClassName.get(
									elementUtils.getPackageOf(parentPresenterElement).getQualifiedName().toString(),
									parentPresenterElement.getSimpleName().toString().replaceAll("Presenter", "MVP"))
							);
						}
					}

					break;
			}

			return super.visitVariable(e, aVoid);
		}
	}

	public PresenterScanner(Elements elementUtils, Types typeUtils, TypeElement presenterElement) {
		this.elementUtils = elementUtils;
		this.typeUtils = typeUtils;
		this.presenterElement = presenterElement;

		this.packageName = Symbol.class.cast(this.presenterElement).packge().getQualifiedName().toString();

		this.api = ClassName.get(packageName, cutApiName(presenterElement));

		viewApi = ClassName.get(packageName, api.simpleName() + ".View");
		viewImpl = ClassName.get(packageName, api.simpleName() + "View");

		presenterApi = ClassName.get(packageName, api.simpleName() + ".Presenter");
		presenterImpl = ClassName.get(packageName, api.simpleName() + "Presenter");
	}

	private String cutApiName(TypeElement presenterTypeElement) {
		final String elementName = presenterTypeElement.getSimpleName().toString();
		return elementName.substring(0, elementName.length() - "Presenter".length());
	}

	protected void scan() {
		final GenerateMVP generateAnnotationMVP = presenterElement.getAnnotation(GenerateMVP.class);

		if (generateAnnotationMVP.mvpInfo().priority() != -2) {
			processGenerateMVPInfo(generateAnnotationMVP.mvpInfo());
		}

		new InnerScanner().scan(presenterElement);

		if (generateAnnotationMVP.nameTokens().length > 0) {
			processGenerateNameTokens(generateAnnotationMVP.nameTokens());
		}
	}

	protected void processGenerateNameTokens(GenerateNameToken[] generateNameTokens) {

	}

	protected void processGenerateMVPInfo(GenerateMVPInfo generateMVPInfo) {

	}

	protected void processRevealIn(ClassName parentMVP) {

	}

	protected void processNestedSlot(String nestedSlot) {

	}
}
