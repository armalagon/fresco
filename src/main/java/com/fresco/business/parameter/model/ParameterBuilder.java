package com.fresco.business.parameter.model;

import com.fresco.business.general.model.BusinessProcess;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class ParameterBuilder {

    private final String code;
    private final String description;
    private final Parameter.ValueSourceType valueSourceType;
    private final BusinessProcess process;
    private Set<ParameterValueSource> valueSources;
    private Long minAmount;
    private Long maxAmount;
    private LocalDate minDate;
    private LocalDate maxDate;
    private BigDecimal minTotalAmount;
    private BigDecimal maxTotalAmount;

    private ParameterBuilder(String code, String description, Parameter.ValueSourceType valueSourceType, BusinessProcess process) {
        this.code = code;
        this.description = description;
        this.valueSourceType = valueSourceType;
        this.process = process;
    }

    public ParameterBuilder valueSources(Set<ParameterValueSource> valueSources) {
        this.valueSources = valueSources;
        return this;
    }

    public ParameterBuilder minAmount(Long minAmount) {
        this.minAmount = minAmount;
        return this;
    }

    public ParameterBuilder minAmount() {
        return minAmount(0L);
    }

    public ParameterBuilder maxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
        return this;
    }

    public ParameterBuilder minDate(LocalDate minDate) {
        this.minDate = minDate;
        return this;
    }

    public ParameterBuilder maxDate(LocalDate maxDate) {
        this.maxDate = maxDate;
        return this;
    }

    public ParameterBuilder minTotalAmount(BigDecimal minTotalAmount) {
        this.minTotalAmount = minTotalAmount;
        return this;
    }

    public ParameterBuilder minTotalAmount() {
        return minTotalAmount(BigDecimal.ZERO);
    }

    public ParameterBuilder maxTotalAmount(BigDecimal maxTotalAmount) {
        this.maxTotalAmount = maxTotalAmount;
        return this;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Parameter.ValueSourceType getValueSourceType() {
        return valueSourceType;
    }

    public BusinessProcess getProcess() {
        return process;
    }

    public Set<ParameterValueSource> getValueSources() {
        return valueSources;
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

    public BigDecimal getMinTotalAmount() {
        return minTotalAmount;
    }

    public BigDecimal getMaxTotalAmount() {
        return maxTotalAmount;
    }

    public static ParameterBuilder create(String code, String description, Parameter.ValueSourceType valueSourceType, BusinessProcess process) {
        return new ParameterBuilder(code, description, valueSourceType, process);
    }
}
