package com.gromoks.onlinemart.web.templater;

import com.gromoks.onlinemart.web.entity.TemplateMode;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static com.gromoks.onlinemart.web.entity.TemplateMode.getByName;

public class ThymeLeafPageGenerator {
    private static final String HTML_DIR = "templates/";

    private static final String CSS_DIR = "templates/css/";

    private static final String HTML_FILE_EXTENSION = ".ftl";

    private static final String CSS_FILE_EXTENSION = ".css";

    private static final String HTML_TEMPLATE_MODE = "HTML";

    private static final String CSS_TEMPLATE_MODE = "CSS";

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

    public String getPage(String filename, TemplateMode templateMode, String variableName, Object object) {
        initResolver(templateMode);

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable(variableName, object);

        return engine.process(filename, context);
    }

    public String getPage(String filename, TemplateMode templateMode) {
        initResolver(templateMode);

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        return engine.process(filename, new Context());
    }

    private ThymeLeafPageGenerator() {
        resolver = new ClassLoaderTemplateResolver();
    }

    private void initResolver(TemplateMode templateMode) {
        if (templateMode == getByName(HTML_TEMPLATE_MODE)) {
            resolver.setTemplateMode(HTML_TEMPLATE_MODE);
            resolver.setSuffix(HTML_FILE_EXTENSION);
            resolver.setPrefix(HTML_DIR);
        } else if (templateMode == getByName(CSS_TEMPLATE_MODE)) {
            resolver.setTemplateMode(CSS_TEMPLATE_MODE);
            resolver.setSuffix(CSS_FILE_EXTENSION);
            resolver.setPrefix(CSS_DIR);
        }
    }
}
