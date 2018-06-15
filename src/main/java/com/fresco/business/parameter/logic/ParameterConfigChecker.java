package com.fresco.business.parameter.logic;

import com.fresco.business.parameter.exception.WrongParameterConfiguration;
import com.fresco.business.parameter.model.Parameter;
import com.fresco.business.parameter.model.ValueSourceType;
import java.time.LocalDate;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
@Singleton
@Startup
public class ParameterConfigChecker {

    private static final String ERROR_FOR_ALL_CONSTRAINTS_WERE_CONFIGURED = "allConstraintsWereConfigured";
    private static final String ERROR_FOR_NO_CONSTRAINTS_ALLOWED = "noConstraintsAllowed";
    private static final String ERROR_FOR_DATA_TYPE_MISMATCH = "dataTypeMismatch";
    private static final String ERROR_FOR_NO_CONFIGURATION_REQUIRED = "noConfigurationRequired";

    private static final String KEY_FOR_DATE_DATATYPE = "{[].dataType.date}";
    private static final String KEY_FOR_TOTAL_DATATYPE = "{[].dataType.total}";
    private static final String KEY_FOR_AMOUNT_OR_TOTAL_DATATYPE = "{[].dataType.amountOrTotal}";

    @PostConstruct
    public void validateConfigOnStartup() {

    }

    public boolean atLeastOneConstraintIsConfigured(final Parameter parameter) {
        return parameter.getMinAmount() != null || parameter.getMaxAmount() != null || parameter.getMinDate() != null ||
                parameter.getMaxDate() != null || parameter.getMinTotal() != null || parameter.getMaxTotal() != null;
    }

    public boolean allConstraintsAreConfigured(final Parameter parameter) {
        return parameter.getMinAmount() != null && parameter.getMaxAmount() != null && parameter.getMinDate() != null &&
                parameter.getMaxDate() != null && parameter.getMinTotal() != null && parameter.getMaxTotal() != null;
    }

    public boolean dateIsConfigured(final Parameter parameter) {
        return !(parameter.getMinDate() == null && parameter.getMaxDate() == null);
    }

    public boolean totalIsConfigured(final Parameter parameter) {
        return !(parameter.getMinTotal() == null && parameter.getMaxTotal() == null);
    }

    public boolean atLeastOneNumericConstraintIsConfigured(final Parameter parameter) {
        return !(parameter.getMinAmount() == null && parameter.getMaxAmount() == null && parameter.getMinTotal() == null &&
                parameter.getMaxTotal() == null);
    }

    public boolean isIntegerType(final Object value) {
        return value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long;
    }

    public void validateConstraintConfig(final Parameter parameter) throws WrongParameterConfiguration {
        final boolean atLeastOneConstraintIsConfigured = atLeastOneConstraintIsConfigured(parameter);

        if (!ValueSourceType.SIMPLE_VALUE.equals(parameter.getValueSourceType()) && atLeastOneConstraintIsConfigured) {
            throw new WrongParameterConfiguration(ERROR_FOR_NO_CONSTRAINTS_ALLOWED, parameter.getCode());
        }

        if (allConstraintsAreConfigured(parameter)) {
            throw new WrongParameterConfiguration(ERROR_FOR_ALL_CONSTRAINTS_WERE_CONFIGURED, parameter.getCode());
        }

        Object value = parameter.getValue();

        if (value != null && ValueSourceType.SIMPLE_VALUE.equals(parameter.getValueSourceType()) && atLeastOneConstraintIsConfigured) {
            if (value instanceof Number) {
                if (dateIsConfigured(parameter)) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_DATE_DATATYPE,
                            value.getClass().getSimpleName(), parameter.getCode());
                }

                if (isIntegerType(value) && totalIsConfigured(parameter)) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_TOTAL_DATATYPE,
                            value.getClass().getSimpleName(), parameter.getCode());
                }
            } else if (value instanceof LocalDate) {
                if (atLeastOneNumericConstraintIsConfigured(parameter)) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_AMOUNT_OR_TOTAL_DATATYPE,
                            value.getClass().getSimpleName(), parameter.getCode());
                }
            } else {
                // Boolean, String, etc...
                throw new WrongParameterConfiguration(ERROR_FOR_NO_CONFIGURATION_REQUIRED, parameter.getCode(),
                        value.getClass().getSimpleName());
            }
        }
    }

}
