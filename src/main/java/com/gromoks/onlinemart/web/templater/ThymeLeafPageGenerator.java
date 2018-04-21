package com.gromoks.onlinemart.web.templater;

import com.gromoks.onlinemart.web.entity.TemplateMode;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;

import static com.gromoks.onlinemart.web.entity.TemplateMode.CSS;
import static com.gromoks.onlinemart.web.entity.TemplateMode.HTML;
import static com.gromoks.onlinemart.web.entity.TemplateMode.getByName;

public class ThymeLeafPageGenerator {
    private static final String HTML_DIR = "templates/";

    private static final String CSS_DIR = "templates/css/";

    private static final String HTML_FILE_EXTENSION = ".html";

    private static final String CSS_FILE_EXTENSION = ".css";

    private static final String HTML_TEMPLATE_MODE = "HTML";

    private static final String CSS_TEMPLATE_MODE = "CSS";

    private final ClassLoaderTemplateResolver htmlResolver;

    private final ClassLoaderTemplateResolver cssResolver;

    private final TemplateEngine htmlEngine;

    private final TemplateEngine cssEngine;

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

    public String getHtmlPage(String filename, Map<String, Object> map) {
        Context context = new Context();

        for (Map.Entry<String, Object> element : map.entrySet()) {
            context.setVariable(element.getKey(), element.getValue());
        }

        return htmlEngine.process(filename, context);
    }

    public String getCssPage(String filename) {
        return cssEngine.process(filename, new Context());
    }

    private ThymeLeafPageGenerator() {
        htmlResolver = new ClassLoaderTemplateResolver();
        initResolver(HTML);
        htmlEngine = new TemplateEngine();
        htmlEngine.setTemplateResolver(htmlResolver);

        cssResolver = new ClassLoaderTemplateResolver();
        initResolver(CSS);
        cssEngine = new TemplateEngine();
        cssEngine.setTemplateResolver(cssResolver);
    }

    private void initResolver(TemplateMode templateMode) {
        if (templateMode == getByName(HTML_TEMPLATE_MODE)) {
            htmlResolver.setTemplateMode(HTML_TEMPLATE_MODE);
            htmlResolver.setSuffix(HTML_FILE_EXTENSION);
            htmlResolver.setPrefix(HTML_DIR);
        } else if (templateMode == getByName(CSS_TEMPLATE_MODE)) {
            cssResolver.setTemplateMode(CSS_TEMPLATE_MODE);
            cssResolver.setSuffix(CSS_FILE_EXTENSION);
            cssResolver.setPrefix(CSS_DIR);
        }
    }
}
