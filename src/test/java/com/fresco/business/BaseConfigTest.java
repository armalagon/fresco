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

    protected static final String JDBC_URL = System.getProperty("jdbc.url");
    protected static final String JDBC_USER = System.getProperty("jdbc.user");
    protected static final String JDBC_PASSWORD = System.getProperty("jdbc.password");

    protected static final String APPLICATION_PROMPT = System.getProperty("app.prompt") + " ";
    protected static final String METHOD_SEPARATOR = "-------------------------------------------------------";

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

    protected String getAppPrompt() {
        return APPLICATION_PROMPT;
    }

    protected String getCurrentlyExecutingTestMethod() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        String methodName = null;

        if (!(stack == null || stack.length == 0 || stack.length < 4)) {
            methodName = stack[3].getMethodName();
        }

        return methodName;
    }

    protected String promptCurrentlyExecutingTestMethod() {
        StringBuilder prompt = new StringBuilder();
        prompt.append(METHOD_SEPARATOR).append(System.lineSeparator());
        prompt.append(getAppPrompt()).append(getCurrentlyExecutingTestMethod()).append(System.lineSeparator());
        prompt.append(METHOD_SEPARATOR).append(System.lineSeparator());
        return prompt.toString();
    }

}
