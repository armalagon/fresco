package com.fresco.business.parameter.logic;

import com.fresco.business.BaseConfigTest;
import java.sql.SQLException;
import org.junit.Test;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class ParameterConfigTest extends BaseConfigTest {

    @Test
    public void testParameterProvider() throws SQLException {
        ParameterProvider parameterProvider = new ParameterProvider();

        parameterProvider.context = context;
        parameterProvider.findAll().stream()
                .forEach(p -> {
                    System.out.println("-> " + p);
                    System.out.println("->" + p.getSources());
                });
    }

}
