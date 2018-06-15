package com.fresco.business.parameter.model;

import com.fresco.business.general.model.BusinessProcess;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class Parameter {

    private final Integer id;
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
        this.id = id;
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

    public Integer getId() {
        return id;
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

    @Override
    public String toString() {
        return "Parameter{" + "id=" + id + ", code=" + code + ", value=" + value + ", unitOfMeasurement=" + unitOfMeasurement + '}';
    }

}
