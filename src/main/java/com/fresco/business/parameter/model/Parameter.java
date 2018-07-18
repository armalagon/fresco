package com.fresco.business.parameter.model;

import com.fresco.business.general.model.BusinessProcessType;
import com.fresco.business.parameter.exception.AllConstraintsConfigured;
import com.fresco.business.parameter.exception.NoConfigurationRequired;
import com.fresco.business.parameter.exception.WrongParameterConfiguration;
import com.zacate.bean.BeanUtils;
import com.zacate.conversion.DefaultDatatypeConverter;
import com.zacate.i18n.Localized;
import com.zacate.identifier.IntegerReadOnlyAndStringNaturalIdentifier;
import com.zacate.jdbc.JDBCUtils;
import com.zacate.util.ErrorCollector;
import com.zacate.util.SimpleTextSearch;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class Parameter extends IntegerReadOnlyAndStringNaturalIdentifier implements Localized {

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
        this.sources = builder.sources;
    }

    @Override
    public String getCode() {
        return parameterType.getCode();
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    @Override
    public String getMessage() {
        return parameterType.getMessage();
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

    public Object convertValue() throws ClassNotFoundException {
        return DefaultDatatypeConverter.getInstance().getValue(value, Class.forName(dataType));
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

    public List<String> validate() {
        if (minAmount == null && maxAmount == null && minDate == null && maxDate == null && minTotal == null && maxTotal == null) {
            return Collections.emptyList();
        }

        ErrorCollector errors = new ErrorCollector();

        boolean atLeastOneIsConfigured = !(minAmount == null && maxAmount == null && minDate == null && maxDate == null &&
                minTotal == null && maxTotal == null);

        if (!ValueSourceType.SIMPLE_VALUE.equals(valueSourceType) && atLeastOneIsConfigured) {
            errors.add(new NoConfigurationRequired(getCode()));
        }

        if (minAmount != null && maxAmount != null && minDate != null && maxDate != null && minTotal != null && maxTotal != null) {
            errors.add(new AllConstraintsConfigured(getCode()));
        }

        if (value != null && ValueSourceType.SIMPLE_VALUE.equals(valueSourceType) && atLeastOneIsConfigured) {
            Object valueBasedOnType;
            try {
                valueBasedOnType = convertValue();
            } catch (ClassNotFoundException ex) {
                errors.add(ex);
                return errors.getMessages();
            }

            if (valueBasedOnType instanceof Number) {
                if (!(minDate == null && maxDate == null)) {
                    errors.add(new WrongParameterConfiguration(getCode(), dataType, WrongParameterConfiguration.ConstraintType.DATE));
                }

                if (BeanUtils.isIntegerType(valueBasedOnType) && !(minTotal == null && maxTotal == null)) {
                    errors.add(new WrongParameterConfiguration(getCode(), dataType, WrongParameterConfiguration.ConstraintType.TOTAL));
                }

                if (valueBasedOnType instanceof BigDecimal && !(minAmount == null && maxAmount == null)) {
                    errors.add(new WrongParameterConfiguration(getCode(), dataType, WrongParameterConfiguration.ConstraintType.AMOUNT));
                }
            } else if (BeanUtils.isDateTimeType(valueBasedOnType) && !(minAmount == null && maxAmount == null && minTotal == null &&
                    maxTotal == null)) {
                errors.add(new WrongParameterConfiguration(getCode(), dataType, WrongParameterConfiguration.ConstraintType
                        .AMOUNT_OR_TOTAL));
            } else {
                // Boolean, String, etc...
                errors.add(new NoConfigurationRequired(getCode(), dataType));
            }
        }

        return errors.getMessages();
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
