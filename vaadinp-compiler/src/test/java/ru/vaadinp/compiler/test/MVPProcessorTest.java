package ru.vaadinp.compiler.test;

import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.vaadinp.compiler.processors.MVPProcessor;
import ru.vaadinp.compiler.processors.VaadinPlatformProcessor1;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Created by oem on 10/16/16.
 */
//@RunWith(JUnit4.class)
public class MVPProcessorTest {

//    @Test
//    public void testSimpleVPComponent() {
//        assertAbout(javaSource())
//                .that(JavaFileObjects.forResource(getClass().getResource("simple/SimpleComponentPresenter.java")))
//                .processedWith(new MVPProcessor())
//                .compilesWithoutError()
//                .and()
//                .generatesSources(
//                        JavaFileObjects.forResource(getClass().getResource("simple/SimpleComponentMVP.java"))
//                );
//    }
//
//    @Test
//    public void testSimpleNestedVPComponent() {
//        assertAbout(javaSource())
//                .that(JavaFileObjects.forResource(getClass().getResource("nested/NestedMVPInfoPresenter.java")))
//                .processedWith(new MVPProcessor())
//                .compilesWithoutError()
//                .and()
//                .generatesSources(
//                        JavaFileObjects.forResource(getClass().getResource("nested/NestedMVPInfoMVP.java")),
//                        JavaFileObjects.forResource(getClass().getResource("nested/VaadinPlatformModule.java"))
//                );
//    }
//
//    @Test
//    public void testSimpleNestedInSlotNestedVPComponent() {
//        assertAbout(javaSource())
//                .that(JavaFileObjects.forResource(getClass().getResource("nestedWithNestedSlot/WithNestedSlotPresenter.java")))
//                .processedWith(new MVPProcessor())
//                .compilesWithoutError()
//                .and()
//                .generatesSources(JavaFileObjects.forResource(getClass().getResource("nestedWithNestedSlot/WithNestedSlotMVP.java")));
//    }
//
//    @Test
//    public void testSimplePlaceVPComponent() {
//        assertAbout(javaSource())
//                .that(JavaFileObjects.forResource(getClass().getResource("place/FullOfOptionsPresenter.java")))
//                .processedWith(new MVPProcessor())
//                .compilesWithoutError()
//                .and()
//                .generatesSources(JavaFileObjects.forResource(getClass().getResource("place/FullOfOptionsMVP.java")));
//    }

}
