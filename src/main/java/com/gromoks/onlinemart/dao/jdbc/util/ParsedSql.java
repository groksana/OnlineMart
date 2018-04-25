package com.gromoks.onlinemart.dao.jdbc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class ParsedSql {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private String originalSql;

    public ParsedSql(String originalSql) {
        this.originalSql = originalSql;
    }

    public String getOriginalSql() {
        return originalSql;
    }

    public String getSubstituteNamedParameterSql() {
        String substitutedSql = originalSql;
        List<String> parameterNameList = getOrderedNamedParameter();
        for (String parameterName : parameterNameList) {
            String namedParameter = ":" + parameterName;
            substitutedSql = substitutedSql.replace(namedParameter, "?");
        }
        log.info("Get sql with substituted named parameters to unnamed(?): {}", substitutedSql);
        return substitutedSql;
    }

    public List<String> getOrderedNamedParameter() {
        List<String> parameterNameList = new LinkedList<>();
        String[] splitSqlByColon = originalSql.split(":");
        for (int i = 1; i < splitSqlByColon.length; i++) {
            String[] splitBySpace = splitSqlByColon[i].split(" ");
            parameterNameList.add(splitBySpace[0]);
        }
        return parameterNameList;
    }
}
