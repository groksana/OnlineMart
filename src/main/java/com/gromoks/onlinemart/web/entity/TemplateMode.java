package com.gromoks.onlinemart.web.entity;

public enum TemplateMode {
    XHTML("XHTML"), CSS("CSS");

    private final String name;

    TemplateMode(String name) {
        this.name = name;
    }

    public static TemplateMode getByName(String name) {
        for (TemplateMode templateMode : values()) {
            if (templateMode.name.equalsIgnoreCase(name)) {
                return templateMode;
            }
        }
        throw new IllegalArgumentException("Sorting type is not supported: " + name);
    }
}
