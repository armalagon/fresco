package com.fresco.business.parameter.exception;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class AllConstraintsConfigured extends WrongParameterConfiguration {

    protected static final String ERROR_FOR_ALL_CONSTRAINTS_ARE_CONFIGURED = "allConstraintsAreConfigured";

    public AllConstraintsConfigured(String parameterCode) {
        super(ERROR_FOR_ALL_CONSTRAINTS_ARE_CONFIGURED, parameterCode);
    }

}
