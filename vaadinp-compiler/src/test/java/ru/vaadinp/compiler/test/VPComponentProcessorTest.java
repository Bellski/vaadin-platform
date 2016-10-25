package ru.vaadinp.compiler.test;

import com.google.testing.compile.JavaFileObjects;
import com.vaadin.annotations.JavaScript;
import com.vaadin.server.BootstrapHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.vaadinp.compiler.processors.VaadinPlatformProcessor;
import ru.vaadinp.compiler.processors.VaadinPlatformProcessor1;


import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Created by oem on 10/16/16.
 */
@RunWith(JUnit4.class)
public class VPComponentProcessorTest {

	@Test
	public void testSimpleVPComponent() {
		assertAbout(javaSource())
				.that(JavaFileObjects.forResource(getClass().getResource("simple/SimplePresenter.java")))
				.processedWith(new VaadinPlatformProcessor1())
				.compilesWithoutError()
				.and()
				.generatesSources(
						JavaFileObjects.forResource(getClass().getResource("simple/SimpleVPComponent.java"))
				);
	}

	@Test
	public void testSimpleNestedVPComponent() {
		assertAbout(javaSource())
			.that(JavaFileObjects.forResource(getClass().getResource("nested/SimpleNestedPresenter.java")))
			.processedWith(new VaadinPlatformProcessor1())
			.compilesWithoutError()
			.and()
			.generatesSources(
					JavaFileObjects.forResource(getClass().getResource("nested/SimpleNestedVPComponent.java")),
					JavaFileObjects.forResource(getClass().getResource("nested/VaadinPlatformModule.java"))
			);
	}

	@Test
	public void testSimpleNestedInSlotNestedVPComponent() {
		assertAbout(javaSource())
			.that(JavaFileObjects.forResource(getClass().getResource("nestedWithNestedSlot/WithNestedSlotPresenter.java")))
			.processedWith(new VaadinPlatformProcessor1())
			.compilesWithoutError()
			.and()
			.generatesSources(JavaFileObjects.forResource(getClass().getResource("nestedWithNestedSlot/WithNestedSlotVPComponent.java")));
	}

	@Test
	public void testSimplePlaceVPComponent() {
		assertAbout(javaSource())
			.that(JavaFileObjects.forResource(getClass().getResource("place/SimplePlacePresenter.java")))
			.processedWith(new VaadinPlatformProcessor1())
			.compilesWithoutError()
			.and()
			.generatesSources(JavaFileObjects.forResource(getClass().getResource("place/SimplePlaceVPComponent.java")));
	}

}
