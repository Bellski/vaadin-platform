package ru.vaadinp.compiler2.test;

import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.vaadinp.compiler.MVPProcessor;

import javax.tools.JavaFileObject;
import java.util.Arrays;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

/**
 * Created by Aleksandr on 05.11.2016.
 */
@RunWith(JUnit4.class)
public class MVPProccessorTest2 {
    @Test
    public void testMVP() {
        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(getClass().getResource("component/SimpleComponentPresenter.java")))
                .processedWith(new MVPProcessor(true))
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
                .processedWith(new MVPProcessor(true))
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
                .processedWith(new MVPProcessor(true))
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
                .processedWith(new MVPProcessor(true))
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
                .processedWith(new MVPProcessor(true))
                .compilesWithoutError()
                .and()
                .generatesSources(
                        JavaFileObjects.forResource(getClass().getResource("full/FullOfOptionsMVP.java")),
                        JavaFileObjects.forResource(getClass().getResource("full/FullOfOptionsTokenSet.java"))
                );
    }

    @Test
    public void testApplication() {
        final JavaFileObject applicationPresenter = JavaFileObjects.forResource(getClass().getResource("application/ApplicationPresenter.java"));
        final JavaFileObject expectedApplicationMVP = JavaFileObjects.forResource(getClass().getResource("application/ApplicationMVP.java"));

        final JavaFileObject homePresenter = JavaFileObjects.forResource(getClass().getResource("application/home/HomePresenter.java"));
        final JavaFileObject expectedHomeMVP = JavaFileObjects.forResource(getClass().getResource("application/home/HomeMVP.java"));
        final JavaFileObject expectedHomeTokenSet = JavaFileObjects.forResource(getClass().getResource("application/home/HomeTokenSet.java"));

        final JavaFileObject expectedApplicationModule = JavaFileObjects.forResource(getClass().getResource("application/ApplicationApplicationModule.java"));

        assertAbout(javaSources())
            .that(Arrays.asList(applicationPresenter, homePresenter))
            .processedWith(new MVPProcessor(true))
            .compilesWithoutError()
            .and()
            .generatesSources(
                expectedApplicationMVP,
                expectedHomeMVP,
                expectedHomeTokenSet,
                expectedApplicationModule
            );

    }

    @Test
    public void testApplicationWithGatekeeper() {
        final JavaFileObject applicationPresenter = JavaFileObjects.forResource(getClass().getResource("gatekeeper/ApplicationPresenter.java"));
        final JavaFileObject expectedApplicationMVP = JavaFileObjects.forResource(getClass().getResource("gatekeeper/ApplicationMVP.java"));

        final JavaFileObject homePresenter = JavaFileObjects.forResource(getClass().getResource("gatekeeper/home/HomePresenter.java"));
        final JavaFileObject expectedHomeMVP = JavaFileObjects.forResource(getClass().getResource("gatekeeper/home/HomeMVP.java"));
        final JavaFileObject expectedHomeTokenSet = JavaFileObjects.forResource(getClass().getResource("gatekeeper/home/HomeTokenSet.java"));

        final JavaFileObject gkPresenter = JavaFileObjects.forResource(getClass().getResource("gatekeeper/gk/TestGateKeeperPresenter.java"));
        final JavaFileObject gkMVP = JavaFileObjects.forResource(getClass().getResource("gatekeeper/gk/TestGateKeeperMVP.java"));

        final JavaFileObject expectedApplicationModule = JavaFileObjects.forResource(getClass().getResource("gatekeeper/ApplicationApplicationModule.java"));

        assertAbout(javaSources())
            .that(Arrays.asList(applicationPresenter, homePresenter, gkPresenter))
            .processedWith(new MVPProcessor(true))
            .compilesWithoutError()
            .and()
            .generatesSources(
                expectedApplicationMVP,
                expectedHomeMVP,
                gkMVP,
                expectedHomeTokenSet,
                expectedApplicationModule
            );

    }

}
