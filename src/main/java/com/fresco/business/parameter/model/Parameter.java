package com.fresco.business.parameter.model;

import com.fresco.business.general.model.BusinessProcessType;
import com.zacate.identifier.NaturalIdentifier;
import com.zacate.jdbc.JDBCUtils;
import com.zacate.model.ReadOnlyIdentifier;
import com.zacate.text.SimpleTextSearch;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class Parameter extends ReadOnlyIdentifier<Integer> implements NaturalIdentifier<String> {

    private final ParameterType parameterType;
    private final String dataType;
    private String value;
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
                .map(source -> new ParameterSource(source, this))
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
        return SimpleTextSearch.search(text)
                .field(parameterType, valueSourceType, unitOfMeasurement, businessProcessType)
                .field(dataType, value)
                .field(businessProcessType.getCategory())
                .contains();
    }

    /*
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
            throw WrongParameterConfiguration.noConstraintsAllowed(getCode());
        }

        if (allConstraintsAreConfigured()) {
            throw WrongParameterConfiguration.allConstraintsAreConfigured(getCode());
        }

        if (realValue != null && ValueSourceType.SIMPLE_VALUE.equals(valueSourceType) && atLeastOneConstraintIsConfigured()) {
            if (realValue instanceof Number) {
                if (dateIsConfigured()) {
                    throw WrongParameterConfiguration.dataTypeMismatch(KEY_FOR_DATE_DATATYPE,
                            realValue.getClass().getSimpleName(), getCode());
                }

                if (valueIsIntegerType() && totalIsConfigured()) {
                    throw WrongParameterConfiguration.dataTypeMismatch(KEY_FOR_TOTAL_DATATYPE,
                            realValue.getClass().getSimpleName(), getCode());
                }
            } else if (realValue instanceof LocalDate) {
                if (atLeastOneNumericConstraintIsConfigured()) {
                    throw WrongParameterConfiguration.dataTypeMismatch(KEY_FOR_AMOUNT_OR_TOTAL_DATATYPE,
                            realValue.getClass().getSimpleName(), getCode());
                }
            } else {
                // Boolean, String, etc...
                throw WrongParameterConfiguration.noConfigurationRequired(getCode(), realValue.getClass().getSimpleName());
            }
        }
    }
    */

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
        private List<ParameterSource> sources;

        public ParameterBuilder(Integer id, String parameterTypeCode) {
            this.id = id;
            this.parameterType = ParameterType.findByCode(parameterTypeCode);
            this.sources = new ArrayList<>();
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
            this.minDate = JDBCUtils.toLocalDate(minDate);
            return this;
        }

        public ParameterBuilder maxDate(LocalDate maxDate) {
            this.maxDate = minDate;
            return this;
        }

        public ParameterBuilder maxDate(java.sql.Date maxDate) {
            this.maxDate = JDBCUtils.toLocalDate(maxDate);
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

        public ParameterBuilder addSources(List<ParameterSource> sources) {
            this.sources.addAll(sources);
            return this;
        }

        public Parameter build() {
            return new Parameter(this);
        }
    }

}
