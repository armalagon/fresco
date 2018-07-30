package com.fresco.business.parameter.exception;

import com.zacate.exception.BusinessException;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class WrongValue extends BusinessException {

    private static final String ERROR_FOR_VALUE_OUT_OF_BOUNDARIES = "outOfBoundaries";

    public WrongValue(String value, Number min, Number max) {
        super(ERROR_FOR_VALUE_OUT_OF_BOUNDARIES, value, (min != null ? '[' + min.toString() : "(<>"),
                (max != null ? max.toString() + ']' : "<>)"));
    }

}
