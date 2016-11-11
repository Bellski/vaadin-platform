package ru.vaadinp.compiler;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;


import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by oem on 10/16/16.
 */
public class JavaSourceGenerator {
    private static final Configuration configuration = new Configuration(Configuration.VERSION_2_3_23); static {
        configuration.setClassForTemplateLoading(JavaSourceGenerator.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
    }

    private JavaSourceGenerator() {
    }

    public static String generateJavaSource(Object javaClassModel, String templateName) throws IOException, TemplateException {
        final Template template = configuration.getTemplate(templateName);

        try(final StringWriter stringWriter = new StringWriter()) {
            template.process(javaClassModel, stringWriter);

            return stringWriter.toString();
        }
    }
}
