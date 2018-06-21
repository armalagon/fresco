package com.fresco.business;

import com.zacate.jdbc.JDBCUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Before;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class BaseConfigTest {

    private static final String JDBC_URL = System.getProperty("jdbc.url");
    private static final String JDBC_USER = System.getProperty("jdbc.user");
    private static final String JDBC_PASSWORD = System.getProperty("jdbc.password");

    protected Connection cnn;
    protected DSLContext context;

    @Before
    public void initResources() throws SQLException {
        cnn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        context = DSL.using(cnn);
    }

    @After
    public void destroyResources() throws Exception {
        context = null;
        JDBCUtils.close(cnn);
    }
}
