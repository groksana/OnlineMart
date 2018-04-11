package com.gromoks.onlinemart.dao.config;

import com.gromoks.onlinemart.exception.FileLoadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

public class MyDataSource implements DataSource{
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Properties properties = new Properties();
    private final String url;
    private final String username;
    private final String password;

    public MyDataSource() {
        try (InputStream inputStream = MyDataSource.class.getClassLoader().getResourceAsStream("db/database.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("File database.properties can't be loaded: ", e);
            throw new FileLoadException("File database.properties can't be loaded: ", e);
        }

        url = properties.getProperty("onlinemart.jdbc.url");
        username = properties.getProperty("onlinemart.jdbc.username");
        password = properties.getProperty("onlinemart.jdbc.password");
    }

    public Connection getConnection() throws SQLException {
        return getConnection(username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}

