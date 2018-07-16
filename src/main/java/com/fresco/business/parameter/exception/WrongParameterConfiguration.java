package com.fresco.business.parameter.exception;

import com.zacate.exception.BusinessException;
import com.zacate.i18n.Localized;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class WrongParameterConfiguration extends BusinessException {

    protected static final String ERROR_FOR_DATA_TYPE_MISMATCH = "dataTypeMismatch";

    public enum ConstraintType implements Localized {
        DATE, TOTAL, AMOUNT, AMOUNT_OR_TOTAL
    }

    public WrongParameterConfiguration(String parameterCode, String parameterDataType, ConstraintType constraintType) {
        super(ERROR_FOR_DATA_TYPE_MISMATCH, constraintType, parameterDataType, parameterCode);
    }

    protected WrongParameterConfiguration(String errorCode, Object... arguments) {
        super(errorCode, true, arguments);
    }

}
