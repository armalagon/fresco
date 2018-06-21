package com.fresco.business.parameter.exception;

import com.zacate.exception.BusinessException;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class WrongParameterConfiguration extends BusinessException {

    protected static final String ERROR_FOR_ALL_CONSTRAINTS_ARE_CONFIGURED = "allConstraintsAreConfigured";
    protected static final String ERROR_FOR_NO_CONSTRAINTS_ALLOWED = "noConstraintsAllowed";
    protected static final String ERROR_FOR_DATA_TYPE_MISMATCH = "dataTypeMismatch";
    protected static final String ERROR_FOR_NO_CONFIGURATION_REQUIRED = "noConfigurationRequired";

    public WrongParameterConfiguration(String errorCode) {
        this(errorCode, (Object[]) null);
    }

    public WrongParameterConfiguration(String errorCode, Object... arguments) {
        super(errorCode, arguments);
    }

    public WrongParameterConfiguration(String errorCode, boolean expandKeyBasedOnCurrentClass, Object... arguments) {
        super(errorCode, expandKeyBasedOnCurrentClass, arguments);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Static factory methods
    // -------------------------------------------------------------------------------------------------------------------------------------

    public static WrongParameterConfiguration noConstraintsAllowed(String parameterCode) {
        return new WrongParameterConfiguration(ERROR_FOR_NO_CONSTRAINTS_ALLOWED, parameterCode);
    }

    public static WrongParameterConfiguration allConstraintsAreConfigured(String parameterCode) {
        return new WrongParameterConfiguration(ERROR_FOR_ALL_CONSTRAINTS_ARE_CONFIGURED, parameterCode);
    }

    public static WrongParameterConfiguration dataTypeMismatch(String keyForDataType, String valueDataType, String parameterCode) {
        return new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, keyForDataType, valueDataType, parameterCode);
    }

    public static WrongParameterConfiguration noConfigurationRequired(String parameterCode, String valueDataType) {
        return new WrongParameterConfiguration(ERROR_FOR_NO_CONFIGURATION_REQUIRED, parameterCode, valueDataType);
    }

}
