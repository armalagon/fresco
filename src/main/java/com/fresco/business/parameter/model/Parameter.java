package com.fresco.business.parameter.model;

import com.fresco.business.general.model.BusinessProcess;
import com.fresco.business.general.model.NaturalIdentifier;
import com.fresco.business.parameter.exception.WrongParameterConfiguration;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface Parameter extends NaturalIdentifier<String> {

    String ERROR_FOR_ALL_CONSTRAINTS_WERE_CONFIGURED = "allConstraintsWereConfigured";
    String ERROR_FOR_NO_CONSTRAINTS_ALLOWED = "noConstraintsAllowed";
    String ERROR_FOR_DATA_TYPE_MISMATCH = "dataTypeMismatch";
    String ERROR_FOR_NO_CONFIGURATION_REQUIRED = "noConfigurationRequired";

    String KEY_FOR_DATE_DATATYPE = "{[].dataType.date}";
    String KEY_FOR_TOTAL_DATATYPE = "{[].dataType.total}";
    String KEY_FOR_AMOUNT_OR_TOTAL_DATATYPE = "{[].dataType.amountOrTotal}";

    /**
     * <p>Remarks: It's optional. The value could be inferred from the type returned by the getValue method</p>
     *
     * @return The fully qualified name of the java class representing the value
     */
    String getDataType();

    /**
     * <p>Current value configured for this parameter</p>
     *
     * @param <V> Represents the datatype of the parameter's value
     * @return Current value
     */
    <V> V getValue();

    ValueSourceType getValueSourceType();

    BusinessProcess getProcess();

    Long getMinAmount();

    Long getMaxAmount();

    LocalDate getMinDate();

    LocalDate getMaxDate();

    BigDecimal getMinTotalAmount();

    BigDecimal getMaxTotalAmount();

    default void validateConstraintConfiguration() throws WrongParameterConfiguration {
        final boolean atLeastOneConstraintIsConfigured = getMinAmount() != null || getMaxAmount() != null || getMinDate() != null ||
                getMaxDate() != null || getMinTotalAmount() != null || getMaxTotalAmount() != null;

        if (!ValueSourceType.SIMPLE_VALUE.equals(getValueSourceType()) && atLeastOneConstraintIsConfigured) {
            throw new WrongParameterConfiguration(ERROR_FOR_NO_CONSTRAINTS_ALLOWED, getCode());
        }

        if (getMinAmount() != null && getMaxAmount() != null && getMinDate() != null && getMaxDate() != null && getMinTotalAmount() != null
                && getMaxTotalAmount() != null) {
            throw new WrongParameterConfiguration(ERROR_FOR_ALL_CONSTRAINTS_WERE_CONFIGURED, getCode());
        }

        Object value = getValue();

        if (value != null && ValueSourceType.SIMPLE_VALUE.equals(getValueSourceType())) {
            if (value instanceof Number) {
                if (!(getMinDate() == null && getMaxDate() == null)) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_DATE_DATATYPE,
                            value.getClass().getSimpleName(), getCode());
                }
                if ((value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long) &&
                        !(getMinTotalAmount() == null && getMaxTotalAmount() == null)) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_TOTAL_DATATYPE,
                            value.getClass().getSimpleName(), getCode());
                }
            } else if (value instanceof LocalDate) {
                if (getMinAmount() != null || getMaxAmount() != null || getMinTotalAmount() != null || getMaxTotalAmount() != null) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_AMOUNT_OR_TOTAL_DATATYPE,
                            value.getClass().getSimpleName(), getCode());
                }
            } else {
                // Boolean, String, etc...
                if (atLeastOneConstraintIsConfigured) {
                    throw new WrongParameterConfiguration(ERROR_FOR_NO_CONFIGURATION_REQUIRED, getCode(),
                            value.getClass().getSimpleName());
                }
            }
        }
    }
}
