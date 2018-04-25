package com.gromoks.onlinemart.dao.jdbc.template;

import com.gromoks.onlinemart.dao.jdbc.config.DataSource;
import com.gromoks.onlinemart.dao.jdbc.mapper.RowMapper;
import com.gromoks.onlinemart.dao.jdbc.util.ParsedSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class NamedParameterJdbcTemplate {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Connection connection;

    public NamedParameterJdbcTemplate(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> query(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) {
        List<T> resultList = new ArrayList<>();
        ParsedSql parsedSql = new ParsedSql(sql);
        List<String> orderedNamedParameter = parsedSql.getOrderedNamedParameter();
        String substituteNamedParametersSql = parsedSql.getSubstituteNamedParameterSql();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(substituteNamedParametersSql);

            int index = 1;
            for (String parameter : orderedNamedParameter) {
                Object parameterValue = paramMap.get(parameter);
                String simpleClassName = parameterValue.getClass().getSimpleName();
                switch (simpleClassName) {
                    case "Integer":
                        preparedStatement.setInt(index, (Integer) parameterValue);
                        index++;
                        break;
                    case "String":
                        preparedStatement.setString(index, (String) parameterValue);
                        index++;
                        break;
                    case "Double":
                        preparedStatement.setDouble(index, (Double) parameterValue);
                        index++;
                        break;
                    default:
                        log.error("Unknown data type: {}", simpleClassName);
                        throw new IllegalArgumentException("Unknown data type: " + simpleClassName);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    T record = rowMapper.mapRow(resultSet);
                    resultList.add(record);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
