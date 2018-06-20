package com.fresco.business.parameter.model;

import com.fresco.business.general.model.BusinessProcessType;
import com.fresco.business.parameter.exception.WrongParameterConfiguration;
import com.zacate.identifier.NaturalIdentifier;
import com.zacate.model.ReadOnlyIdentifier;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class Parameter extends ReadOnlyIdentifier<Integer> implements NaturalIdentifier<String> {

    private static final String ERROR_FOR_ALL_CONSTRAINTS_WERE_CONFIGURED = "allConstraintsWereConfigured";
    private static final String ERROR_FOR_NO_CONSTRAINTS_ALLOWED = "noConstraintsAllowed";
    private static final String ERROR_FOR_DATA_TYPE_MISMATCH = "dataTypeMismatch";
    private static final String ERROR_FOR_NO_CONFIGURATION_REQUIRED = "noConfigurationRequired";

    private static final String KEY_FOR_DATE_DATATYPE = "{[].dataType.date}";
    private static final String KEY_FOR_TOTAL_DATATYPE = "{[].dataType.total}";
    private static final String KEY_FOR_AMOUNT_OR_TOTAL_DATATYPE = "{[].dataType.amountOrTotal}";

    private final ParameterType parameterType;
    private final String dataType;
    private String value;
    private Object realValue;
    private final ValueSourceType valueSourceType;
    private final UnitOfMeasurement unitOfMeasurement;
    private final BusinessProcessType businessProcessType;
    private final Long minAmount;
    private final Long maxAmount;
    private final LocalDate minDate;
    private final LocalDate maxDate;
    private final BigDecimal minTotal;
    private final BigDecimal maxTotal;
    private final Set<ParameterSource> sources;

    private Parameter(ParameterBuilder builder) {
        super(builder.id);
        this.parameterType = builder.parameterType;
        this.dataType = builder.dataType;
        this.value = builder.value;
        this.valueSourceType = builder.valueSourceType;
        this.unitOfMeasurement = builder.unitOfMeasurement;
        this.businessProcessType = builder.businessProcessType;
        this.minAmount = builder.minAmount;
        this.maxAmount = builder.maxAmount;
        this.minDate = builder.minDate;
        this.maxDate = builder.maxDate;
        this.minTotal = builder.minTotal;
        this.maxTotal = builder.maxTotal;
        this.sources = builder.sources.stream()
                .map(source -> new ParameterSource(source.getId(), source.getCode(), source.getFullyQualifiedClassname(),
                        source.getQuery(), source.getSequenceNumber(), this))
                .collect(Collectors.toSet());
    }

    @Override
    public String getCode() {
        return parameterType.getCode();
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public String getDataType() {
        return dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public <T> T getRealValue(Class<T> clazz) {
        if (value != null && realValue == null) {
            // TODO Convert value
        }
        return (T) realValue;
    }

    public ValueSourceType getValueSourceType() {
        return valueSourceType;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public BusinessProcessType getBusinessProcessType() {
        return businessProcessType;
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

    public Set<ParameterSource> getSources() {
        return Collections.unmodifiableSet(sources);
    }

    public boolean containsIgnoreCase(String text) {
        String __text = Objects.requireNonNull(text).toLowerCase();
        return parameterType.containsIgnoreCase(text) || dataType.toLowerCase().contains(__text) || (value != null &&
                value.toLowerCase().contains(__text)) || valueSourceType.containsIgnoreCase(text) ||
                unitOfMeasurement.containsIgnoreCase(text) || businessProcessType.containsIgnoreCase(text) ||
                businessProcessType.getCategory().containsIgnoreCase(text);
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
        return realValue instanceof Byte || realValue instanceof Short || realValue instanceof Integer || realValue instanceof Long;
    }

    public void validateConstraintConfig() throws WrongParameterConfiguration {
        if (noConstraintConfigured()) {
            return;
        }

        if (!ValueSourceType.SIMPLE_VALUE.equals(valueSourceType) && atLeastOneConstraintIsConfigured()) {
            throw new WrongParameterConfiguration(ERROR_FOR_NO_CONSTRAINTS_ALLOWED, getCode());
        }

        if (allConstraintsAreConfigured()) {
            throw new WrongParameterConfiguration(ERROR_FOR_ALL_CONSTRAINTS_WERE_CONFIGURED, getCode());
        }

        if (realValue != null && ValueSourceType.SIMPLE_VALUE.equals(valueSourceType) && atLeastOneConstraintIsConfigured()) {
            if (realValue instanceof Number) {
                if (dateIsConfigured()) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_DATE_DATATYPE,
                            realValue.getClass().getSimpleName(), getCode());
                }

                if (valueIsIntegerType() && totalIsConfigured()) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_TOTAL_DATATYPE,
                            realValue.getClass().getSimpleName(), getCode());
                }
            } else if (realValue instanceof LocalDate) {
                if (atLeastOneNumericConstraintIsConfigured()) {
                    throw new WrongParameterConfiguration(ERROR_FOR_DATA_TYPE_MISMATCH, KEY_FOR_AMOUNT_OR_TOTAL_DATATYPE,
                            realValue.getClass().getSimpleName(), getCode());
                }
            } else {
                // Boolean, String, etc...
                throw new WrongParameterConfiguration(ERROR_FOR_NO_CONFIGURATION_REQUIRED, getCode(), realValue.getClass().getSimpleName());
            }
        }
    }

    @Override
    public String toString() {
        return "Parameter{" + "id=" + id + ", code=" + getCode() + ", dataType=" + dataType + ", value=" + value + ", valueSourceType=" +
                valueSourceType + ", unitOfMeasurement=" + unitOfMeasurement + ", businessProcessType=" + businessProcessType +
                ", businessProcessCategory=" + businessProcessType.getCategory() + '}';
    }

    public static class ParameterBuilder {
        private Integer id;
        private ParameterType parameterType;
        private String dataType;
        private String value;
        private ValueSourceType valueSourceType;
        private UnitOfMeasurement unitOfMeasurement;
        private BusinessProcessType businessProcessType;
        private Long minAmount;
        private Long maxAmount;
        private LocalDate minDate;
        private LocalDate maxDate;
        private BigDecimal minTotal;
        private BigDecimal maxTotal;
        private Set<ParameterSource> sources;

        public ParameterBuilder(Integer id, String parameterTypeCode) {
            this.id = id;
            this.parameterType = ParameterType.findByCode(parameterTypeCode);
            this.sources = new HashSet<>();
        }

        public ParameterBuilder dataType(String dataType) {
            this.dataType = dataType;
            return this;
        }

        public ParameterBuilder value(String value) {
            this.value = value;
            return this;
        }

        public ParameterBuilder valueSourceType(String code) {
            this.valueSourceType = ValueSourceType.findByCode(code);
            return this;
        }

        public ParameterBuilder unitOfMeasurement(String code) {
            this.unitOfMeasurement = UnitOfMeasurement.findByCode(code);
            return this;
        }

        public ParameterBuilder businessProcessType(String code) {
            this.businessProcessType = BusinessProcessType.findByCode(code);
            return this;
        }

        public ParameterBuilder minAmount(Long minAmount) {
            this.minAmount = minAmount;
            return this;
        }

        public ParameterBuilder maxAmount(Long maxAmount) {
            this.maxAmount = maxAmount;
            return this;
        }

        public ParameterBuilder minDate(LocalDate minDate) {
            this.minDate = minDate;
            return this;
        }

        public ParameterBuilder minDate(java.sql.Date minDate) {
            // TODO Mover a clase utilitaria: JDBCUtils
            this.minDate = minDate != null ? LocalDate.ofEpochDay(minDate.getTime()) : null;
            return this;
        }

        public ParameterBuilder maxDate(LocalDate maxDate) {
            this.maxDate = maxDate;
            return this;
        }

        public ParameterBuilder maxDate(java.sql.Date maxDate) {
            this.maxDate = maxDate != null ? LocalDate.ofEpochDay(maxDate.getTime()) : null;
            return this;
        }

        public ParameterBuilder minTotal(BigDecimal minTotal) {
            this.minTotal = minTotal;
            return this;
        }

        public ParameterBuilder maxTotal(BigDecimal maxTotal) {
            this.maxTotal = maxTotal;
            return this;
        }

        public ParameterBuilder addSources(Collection<ParameterSource> sources) {
            this.sources.addAll(sources);
            return this;
        }

        public Parameter build() {
            return new Parameter(this);
        }
    }

}
