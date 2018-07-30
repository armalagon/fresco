package com.fresco.business.parameter.logic;

import com.fresco.business.BaseConfigTest;
import com.fresco.business.parameter.exception.ParameterNotFound;
import com.fresco.business.parameter.model.ParameterType;
import java.sql.SQLException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class ParameterProviderTest extends BaseConfigTest {

    ParameterProvider parameterProvider;

    @Before
    public void initProvider() {
        parameterProvider = new ParameterProvider();
        parameterProvider.context = context;
        parameterProvider.onInit();
    }

    @Test
    public void testFindAll() throws SQLException, ParameterNotFound {
        System.out.println(promptCurrentlyExecutingTestMethod());
        System.out.println("-> Buscar por texto");
        parameterProvider.findByText("autenticación").stream()
                .forEach(p -> {
                    System.out.println("-> " + p);
                });

        System.out.println(METHOD_SEPARATOR);
        Integer months = parameterProvider.getValue(ParameterType.PASSWORD_EXPIRATION, Integer.class).orElse(null);
        System.out.println("-> ParameterType.PASSWORD_EXPIRATION = " + months);

        System.out.println(METHOD_SEPARATOR);
        String oldValue = parameterProvider.getValue(ParameterType.MINIMUM_PASSWORD_LENGTH, String.class).orElse(null);
        List<String> errors = parameterProvider.updateValue(ParameterType.MINIMUM_PASSWORD_LENGTH, "6");
        String newValue = parameterProvider.getValue(ParameterType.MINIMUM_PASSWORD_LENGTH, String.class).orElse(null);

        System.out.println("-> " + ParameterType.MINIMUM_PASSWORD_LENGTH + ", oldValue: " + oldValue + "; newValue: " + newValue);
        errors.forEach(msg -> System.out.println("-> Error: " + msg));
    }

}
