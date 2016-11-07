package ru.vaadinp.compiler2.test;

import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.vaadinp.compiler2.MVPProcessor;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Created by Aleksandr on 05.11.2016.
 */
@RunWith(JUnit4.class)
public class MVPProccessorTest2 {
    @Test
    public void testMVP() {
        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(getClass().getResource("component/SimpleComponentPresenter.java")))
                .processedWith(new MVPProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(
                        JavaFileObjects.forResource(getClass().getResource("component/SimpleComponentMVP.java"))
                );
    }

    @Test
    public void testSimpleNestedMVP() {
        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(getClass().getResource("nested/SimpleNestedPresenter.java")))
                .processedWith(new MVPProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(
                        JavaFileObjects.forResource(getClass().getResource("nested/SimpleNestedMVP.java"))
                );
    }

    @Test
    public void testSimpleNestedWithMVPInfoMVP() {
        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(getClass().getResource("mvpinfo/NestedMVPInfoPresenter.java")))
                .processedWith(new MVPProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(
                        JavaFileObjects.forResource(getClass().getResource("mvpinfo/NestedMVPInfoMVP.java"))
                );
    }

    @Test
    public void testSimpleNestedPlaceMVP() {
        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(getClass().getResource("place/SimplePlacePresenter.java")))
                .processedWith(new MVPProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(
                        JavaFileObjects.forResource(getClass().getResource("place/SimplePlaceMVP.java"))
                );
    }

    @Test
    public void testFullOfOptionsMVP() {
        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(getClass().getResource("full/FullOfOptionsPresenter.java")))
                .processedWith(new MVPProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(
                        JavaFileObjects.forResource(getClass().getResource("full/FullOfOptionsMVP.java")),
                        JavaFileObjects.forResource(getClass().getResource("full/FullOfOptionsTokenSet.java"))
                );
    }
}
