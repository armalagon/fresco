package com.fresco.business.parameter.exception;

import com.zacate.exception.BusinessException;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class WrongParameterConfiguration extends BusinessException {

    public WrongParameterConfiguration(String errorCode) {
        this(errorCode, (Object[]) null);
    }

    public WrongParameterConfiguration(String errorCode, Object... arguments) {
        super(errorCode, arguments);
    }

    public WrongParameterConfiguration(String errorCode, boolean expandKeyBasedOnCurrentClass, Object... arguments) {
        super(errorCode, expandKeyBasedOnCurrentClass, arguments);
    }

}
