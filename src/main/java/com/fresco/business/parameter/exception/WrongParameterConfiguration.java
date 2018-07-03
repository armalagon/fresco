package com.fresco.business.parameter.exception;

import com.zacate.exception.BusinessException;
import com.zacate.i18n.LocalizedEnum;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class WrongParameterConfiguration extends BusinessException {

    protected static final String ERROR_FOR_ALL_CONSTRAINTS_ARE_CONFIGURED = "allConstraintsAreConfigured";
    protected static final String ERROR_FOR_DATA_TYPE_MISMATCH = "dataTypeMismatch";

    public enum ConstraintType implements LocalizedEnum {
        DATE, TOTAL, QUANTITY_OR_TOTAL
    }

    public WrongParameterConfiguration(String parameterCode) {
        super(ERROR_FOR_ALL_CONSTRAINTS_ARE_CONFIGURED, parameterCode);
    }

    public WrongParameterConfiguration(String parameterCode, String parameterDataType, ConstraintType constraintType) {
        super(ERROR_FOR_DATA_TYPE_MISMATCH, constraintType, parameterDataType, parameterCode);
    }

    public WrongParameterConfiguration(String errorCode, Object... arguments) {
        super(errorCode, true, arguments);
    }

}
