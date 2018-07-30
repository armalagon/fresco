package com.fresco.business.parameter.exception;

import com.zacate.exception.BusinessException;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class ParameterNotFound extends BusinessException {

    private static final String ERROR_FOR_PARAMETER_NOT_FOUND = "notFound";

    public ParameterNotFound(String parameterCode) {
        super(ERROR_FOR_PARAMETER_NOT_FOUND, parameterCode);
    }

}
