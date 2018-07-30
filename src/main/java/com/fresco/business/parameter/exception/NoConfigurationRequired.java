package com.fresco.business.parameter.exception;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class NoConfigurationRequired extends WrongParameterConfiguration {

    private static final String ERROR_FOR_NO_CONSTRAINTS_ALLOWED = "noConstraintsAllowed";
    private static final String ERROR_FOR_NO_CONFIGURATION_REQUIRED = "noConfigurationRequired";

    public NoConfigurationRequired(String parameterCode) {
        super(ERROR_FOR_NO_CONSTRAINTS_ALLOWED, parameterCode);
    }

    public NoConfigurationRequired(String parameterCode, String valueDataType) {
        super(ERROR_FOR_NO_CONFIGURATION_REQUIRED, parameterCode, valueDataType);
    }

}
