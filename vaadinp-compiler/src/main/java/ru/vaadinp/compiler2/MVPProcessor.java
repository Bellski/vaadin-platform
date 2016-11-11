package ru.vaadinp.compiler2;

import com.squareup.javapoet.JavaFile;
import com.sun.tools.javac.code.Symbol;
import ru.vaadinp.annotations.GenerateApplicationModule;
import ru.vaadinp.annotations.GenerateMVP;
import ru.vaadinp.vp.PresenterComponent;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by oem on 11/9/16.
 */
public class MVPProcessor extends AbstractProcessor {
	private Messager messager;
	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);

		messager = processingEnv.getMessager();
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		filer = processingEnv.getFiler();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		final Set<? extends Element> rootPresenterElements = roundEnv.getElementsAnnotatedWith(GenerateApplicationModule.class);
		final Set<? extends Element> presenterElements = roundEnv.getElementsAnnotatedWith(GenerateMVP.class);

		if (!rootPresenterElements.isEmpty()) {
			final Element rootPresenterElement = rootPresenterElements.iterator().next();
			final String rootPackageName = Symbol.class.cast(rootPresenterElement).packge().getQualifiedName().toString();

			ApplicationModuleBuilder applicationModuleBuilder = null;

			if (!rootPresenterElements.isEmpty()) {
				applicationModuleBuilder = new ApplicationModuleBuilder(
					rootPresenterElement.getSimpleName().toString().replaceAll("Presenter", ""),
					rootPackageName
				);
			}

			for (Element element : presenterElements) {
				if (isPresenterComponent((TypeElement) element)) {
					writeSource(new MVPBuilder(elementUtils, typeUtils, (TypeElement) element));
				} else {
					final NestedMVPBuilder nestedMVPBuilder = new NestedMVPBuilder(elementUtils, typeUtils, (TypeElement) element);
					writeSource(nestedMVPBuilder);

					if (nestedMVPBuilder.getTokenSetBuilder() != null) {
						writeSource(nestedMVPBuilder.getTokenSetBuilder());

						if (applicationModuleBuilder != null) {
							applicationModuleBuilder.addTokenSet(nestedMVPBuilder.getTokenSetBuilder());
						}
					}

					if (applicationModuleBuilder != null) {
						applicationModuleBuilder.addDeclaration(nestedMVPBuilder.getClassName());
					}
				}
			}

			if (applicationModuleBuilder != null) {
				writeSource(applicationModuleBuilder);
			}
		}

		return true;
	}

	private void writeSource(TypeSpecBuilder mvpBuilder) {
		try {
			JavaFile
				.builder(mvpBuilder.getClassName().packageName(), mvpBuilder.getTypeSpec())
				.skipJavaLangImports(true)
				.build()
				.writeTo(filer);
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			messager.printMessage(Diagnostic.Kind.ERROR, sw.toString());
		}
	}

	private boolean isPresenterComponent(TypeElement presenter) {
		return typeUtils.isSameType(
			typeUtils
				.asElement(
					presenter.getSuperclass()
				).asType(),
			elementUtils.getTypeElement(
				PresenterComponent
					.class
					.getName()
			).asType()
		);
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return new HashSet<String>() {{
			add(GenerateMVP.class.getName());
			add(GenerateApplicationModule.class.getName());
		}};
	}
}
