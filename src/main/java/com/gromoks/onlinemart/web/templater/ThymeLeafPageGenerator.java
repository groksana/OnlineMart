package com.gromoks.onlinemart.web.templater;

import com.gromoks.onlinemart.entity.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.ArrayList;
import java.util.List;

public class ThymeLeafPageGenerator {
    private static final String HTML_DIR = "templates/";

    private static final String TEMPLATE_MODE = "XHTML";

    private static final String FILE_EXTENSION = ".ftl";

    private final ClassLoaderTemplateResolver resolver;

    private static volatile ThymeLeafPageGenerator thymeLeafPageGenerator;

    public static ThymeLeafPageGenerator instance() {
        if (thymeLeafPageGenerator == null) {
            synchronized (ThymeLeafPageGenerator.class) {
                if (thymeLeafPageGenerator == null) {
                    thymeLeafPageGenerator = new ThymeLeafPageGenerator();
                }
            }
        }
        return thymeLeafPageGenerator;
    }

    public String getPage(String filename, String variableName, Object object) {
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable(variableName, object);

        return engine.process(filename, context);
    }

    public String getPage(String filename) {
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        return engine.process(filename, new Context());
    }

    private ThymeLeafPageGenerator() {
        resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode(TEMPLATE_MODE);
        resolver.setSuffix(FILE_EXTENSION);
        resolver.setPrefix(HTML_DIR);
    }
}
