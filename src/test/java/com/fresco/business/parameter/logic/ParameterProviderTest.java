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
public class ParameterProviderTest extends BaseConfigTest {

    @Test
    public void testFindAll() throws SQLException {
        System.out.println(promptCurrentlyExecutingTestMethod());

        ParameterProvider parameterProvider = new ParameterProvider();

        parameterProvider.context = context;
        parameterProvider.onInit();

        System.out.println("-> Buscar por texto");
        parameterProvider.findByText("autenticación").stream()
                .forEach(p -> {
                    System.out.println("-> " + p);
                });
    }

}
