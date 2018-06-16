package com.fresco.business.parameter.model;

import com.fresco.business.general.model.BusinessProcess;
import com.fresco.business.general.model.ReadOnlyIdentifier;
import com.fresco.business.parameter.exception.WrongParameterConfiguration;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class Parameter extends ReadOnlyIdentifier<Integer> {

    private static final String ERROR_FOR_ALL_CONSTRAINTS_WERE_CONFIGURED = "allConstraintsWereConfigured";
    private static final String ERROR_FOR_NO_CONSTRAINTS_ALLOWED = "noConstraintsAllowed";
    private static final String ERROR_FOR_DATA_TYPE_MISMATCH = "dataTypeMismatch";
    private static final String ERROR_FOR_NO_CONFIGURATION_REQUIRED = "noConfigurationRequired";

    private static final String KEY_FOR_DATE_DATATYPE = "{[].dataType.date}";
    private static final String KEY_FOR_TOTAL_DATATYPE = "{[].dataType.total}";
    private static final String KEY_FOR_AMOUNT_OR_TOTAL_DATATYPE = "{[].dataType.amountOrTotal}";

    private final String code;
    private final String dataType;
    private final Object value;
    private final ValueSourceType valueSourceType;
    private final UnitOfMeasurement unitOfMeasurement;
    private final BusinessProcess process;
    private final Long minAmount;
    private final Long maxAmount;
    private final LocalDate minDate;
    private final LocalDate maxDate;
    private final BigDecimal minTotal;
    private final BigDecimal maxTotal;

    public Parameter(Integer id, String code, String dataType, Object value, ValueSourceType valueSourceType,
            UnitOfMeasurement unitOfMeasurement, BusinessProcess process, Long minAmount, Long maxAmount, LocalDate minDate,
            LocalDate maxDate, BigDecimal minTotal, BigDecimal maxTotal) {
        super(id);
        this.code = code;
        this.dataType = dataType;
        this.value = value;
        this.valueSourceType = valueSourceType;
        this.unitOfMeasurement = unitOfMeasurement;
        this.process = process;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.minTotal = minTotal;
        this.maxTotal = maxTotal;
    }

    public String getCode() {
        return code;
    }

    public String getDataType() {
        return dataType;
    }

    public Object getValue() {
        return value;
    }

    public ValueSourceType getValueSourceType() {
        return valueSourceType;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public BusinessProcess getProcess() {
        return process;
    }

    public Long getMinAmount() {
        return minAmount;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public LocalDate getMinDate() {
        return minDate;
    }

    public LocalDate getMaxDate() {
        return maxDate;
    }

    public BigDecimal getMinTotal() {
        return minTotal;
    }

    public BigDecimal getMaxTotal() {
        return maxTotal;
    }

    public boolean atLeastOneConstraintIsConfigured() {
        return !(minAmount == null && maxAmount == null && minDate == null && maxDate == null && minTotal == null && maxTotal == null);
    }

    public boolean allConstraintsAreConfigured() {
        return minAmount != null && maxAmount != null && minDate != null && maxDate != null && minTotal != null && maxTotal != null;
    }

    public boolean noConstraintConfigured() {
        return minAmount == null && maxAmount == null && minDate == null && maxDate == null && minTotal == null && maxTotal == null;
    }

    public boolean dateIsConfigured() {
        return !(minDate == null && maxDate == null);
    }

    public boolean totalIsConfigured() {
        return !(minTotal == null && maxTotal == null);
    }

    public boolean atLeastOneNumericConstraintIsConfigured() {
        return !(minAmount == null && maxAmount == null && minTotal == null && maxTotal == null);
    }

    public boolean valueIsIntegerType() {
        return value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long;
    }

    public void validateConstraintConfig() throws WrongParameterConfiguration {
        if (noConstraintConfigured()) {
            return;
        }

        if (!ValueSourceType.SIMPLE_VALUE.equals(valueSourceType) && atLeastOneConstraintIsConfigured()) {
            throw new WrongParameterConfiguration(ERROR_FOR_NO_CONSTRAINTS_ALLOWED, code);
        }

        if (allConstraintsAreConfigured()) {
            throw new WrongParameterConfiguration(ERROR_FOR_ALL_CONSTRAINTS_WERE_CONFIGURED, code);
        }

        if (value != null && ValueSourceType.SIMPLE_VALUE.equals(valueSourceType) && atLeastOneConstraintIsConfigured()) {
            if (value instanceof Number) {
                if (dateIsConfigured()) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_DATE_DATATYPE,
                            value.getClass().getSimpleName(), code);
                }

                if (valueIsIntegerType() && totalIsConfigured()) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_TOTAL_DATATYPE,
                            value.getClass().getSimpleName(), code);
                }
            } else if (value instanceof LocalDate) {
                if (atLeastOneNumericConstraintIsConfigured()) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_AMOUNT_OR_TOTAL_DATATYPE,
                            value.getClass().getSimpleName(), code);
                }
            } else {
                // Boolean, String, etc...
                throw new WrongParameterConfiguration(ERROR_FOR_NO_CONFIGURATION_REQUIRED, code,
                        value.getClass().getSimpleName());
            }
        }
    }

    @Override
    public String toString() {
        return "Parameter{" + "id=" + id + ", code=" + code + ", value=" + value + ", unitOfMeasurement=" + unitOfMeasurement + '}';
    }

}
