package com.swf.mybatis.generator.internal;

import com.swf.mybatis.generator.api.ConnectionFactory;
import com.swf.mybatis.generator.config.JDBCConnectionConfiguration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class JDBCConnectionFactory implements ConnectionFactory {

    private String userId;

    private String password;

    private String connectionURL;

    private String driverClass;

    private Properties otherProperties;

    public JDBCConnectionFactory(JDBCConnectionConfiguration config) {
        super();
        this.userId = config.getUserId();
        this.password = config.getPassword();
        this.connectionURL = config.getConnectionURL();
        this.driverClass = config.getDriverClass();
        this.otherProperties = config.getProperties();
    }

    public JDBCConnectionFactory() {super();}

    @Override
    public Connection getConnection() throws SQLException {
        Properties props = new Properties();
        if (stringHasValue(userId)) {
            props.setProperty("user", userId);
        }

        if (stringHasValue(password)) {
            props.setProperty("password", password);
        }

        props.putAll(otherProperties);
        Driver driver = getDriver();
        Connection conn = driver.connect(connectionURL,props);
        if (conn == null) {
            throw new SQLException(getString("RuntimeError.7"));
        }
        return conn;
    }

    private Driver getDriver() {
        Driver driver;
        try {
            Class<?> clazz = ObjectFactory.externalClassForName(driverClass);
            driver = (Driver) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(getString("RuntimeError.8"), e);
        }
        return driver;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        // this should only be called when this connection factory is specified in a ConnectionFactory configuration
        userId = properties.getProperty("userId");
        password = properties.getProperty("password");
        connectionURL = properties.getProperty("connectionURL");
        driverClass = properties.getProperty("driverClass");
        otherProperties = new Properties();
        otherProperties.putAll(properties);
        otherProperties.remove("userId");
        otherProperties.remove("password");
        otherProperties.remove("connectionURL");
        otherProperties.remove("driverClass");
    }
}
