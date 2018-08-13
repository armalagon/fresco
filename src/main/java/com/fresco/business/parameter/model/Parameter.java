package com.fresco.business.parameter.model;

import com.fresco.business.general.model.BusinessProcessType;
import com.fresco.business.general.model.GetterInsertable;
import com.fresco.business.general.model.Updatable;
import com.fresco.business.parameter.exception.AllConstraintsConfigured;
import com.fresco.business.parameter.exception.NoConfigurationRequired;
import com.fresco.business.parameter.exception.WrongParameterConfiguration;
import com.fresco.business.parameter.exception.WrongValue;
import com.zacate.bean.ReflectionException;
import com.zacate.bean.Reflections;
import com.zacate.conversion.DefaultDatatypeConverter;
import com.zacate.i18n.Localized;
import com.zacate.identifier.ReadOnlyIntegerAndStringNaturalIdentifier;
import com.zacate.util.Arguments;
import com.zacate.util.ErrorCollector;
import com.zacate.util.NumberUtils;
import com.zacate.util.SimpleTextSearch;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class Parameter extends ReadOnlyIntegerAndStringNaturalIdentifier implements Localized, GetterInsertable, Updatable {

    private final ParameterType parameterType;
    private final String dataType;
    private String value;
    private final ValueSourceType valueSourceType;
    private final UnitOfMeasurement unitOfMeasurement;
    private final BusinessProcessType businessProcessType;
    private final boolean reserved;
    private final Long minAmount;
    private final Long maxAmount;
    private final BigDecimal minTotal;
    private final BigDecimal maxTotal;
    private final Set<ParameterSource> sources;
    private final Integer createdBy;
    private final LocalDateTime createdOn;
    private Integer updatedBy;
    private LocalDateTime updatedOn;

    private Parameter(ParameterBuilder builder) {
        super(builder.id);
        this.parameterType = builder.parameterType;
        this.dataType = builder.dataType;
        this.value = builder.value;
        this.valueSourceType = builder.valueSourceType;
        this.unitOfMeasurement = builder.unitOfMeasurement;
        this.businessProcessType = builder.businessProcessType;
        this.reserved = builder.reserved;
        this.minAmount = builder.minAmount;
        this.maxAmount = builder.maxAmount;
        this.minTotal = builder.minTotal;
        this.maxTotal = builder.maxTotal;
        this.sources = builder.sources;
        this.createdBy = builder.createdBy;
        this.createdOn = builder.createdOn;
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

    public Object convertValue(String newValue) throws ClassNotFoundException {
        return DefaultDatatypeConverter.getInstance().getValue(newValue, Class.forName(dataType));
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

    public boolean isReserved() {
        return reserved;
    }

    public Long getMinAmount() {
        return minAmount;
    }

    public Long getMaxAmount() {
        return maxAmount;
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

    @Override
    public Integer getCreatedBy() {
        return createdBy;
    }

    @Override
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @Override
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    @Override
    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public boolean containsIgnoreCase(String text) {
        return SimpleTextSearch.search(text)
                .field(parameterType, valueSourceType, unitOfMeasurement, businessProcessType)
                .field(dataType, value)
                .field(businessProcessType.getCategory())
                .contains();
    }

    public List<String> validate() {
        return validate(null);
    }

    private List<String> validate(String newValue) {
        if (Arguments.isEmpty(minAmount, maxAmount, minTotal, maxTotal)) {
            return Collections.emptyList();
        }

        ErrorCollector errors = new ErrorCollector();

        boolean atLeastOneIsConfigured = Arguments.isNotEmpty(minAmount, maxAmount, minTotal, maxTotal);

        if (!ValueSourceType.SIMPLE_VALUE.equals(valueSourceType) && atLeastOneIsConfigured) {
            errors.add(new NoConfigurationRequired(getCode()));
        }

        // TODO Create a new method for this validation
        if (minAmount != null && maxAmount != null && minTotal != null && maxTotal != null) {
            errors.add(new AllConstraintsConfigured(getCode()));
        }

        if (value != null && ValueSourceType.SIMPLE_VALUE.equals(valueSourceType) && atLeastOneIsConfigured) {
            Object valueBasedOnType;
            try {
                valueBasedOnType = newValue == null ? convertValue(value) : convertValue(newValue);
            } catch (ClassNotFoundException | IllegalArgumentException | UnsupportedOperationException | ReflectionException ex) {
                errors.add(ex);
                return errors.getMessages();
            }

            if (Reflections.isSomeIntegerType(valueBasedOnType)) {
                if (Arguments.isNotEmpty(minTotal, maxTotal)) {
                    errors.add(new WrongParameterConfiguration(getCode(), dataType, WrongParameterConfiguration.ConstraintType.TOTAL));
                }

                if (Arguments.isNotEmpty(minAmount, maxAmount)) {
                    Long _value = ((Number) valueBasedOnType).longValue();

                    if (NumberUtils.isOutOfBoundaries(_value, minAmount, maxAmount)) {
                        errors.add(new WrongValue(newValue, minAmount, maxAmount));
                    }
                }
            } else if (valueBasedOnType instanceof BigDecimal) {
                if (Arguments.isNotEmpty(minAmount, maxAmount)) {
                    errors.add(new WrongParameterConfiguration(getCode(), dataType, WrongParameterConfiguration.ConstraintType.AMOUNT));
                }

                if (Arguments.isNotEmpty(minTotal, maxTotal)) {
                    if (NumberUtils.isOutOfBoundaries((BigDecimal) valueBasedOnType, minTotal, maxTotal)) {
                        errors.add(new WrongValue(newValue, minTotal, maxTotal));
                    }
                }
            } else {
                // Boolean, String, etc...
                errors.add(new NoConfigurationRequired(getCode(), dataType));
            }
        }

        return errors.getMessages();
    }

    public List<String> doValidateAndUpdate(String newValue) {
        Objects.requireNonNull(newValue, "newValue");
        List<String> errors = validate(newValue);
        if (errors.isEmpty()) {
            value = newValue;
        }
        return errors;
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
        private boolean reserved;
        private Long minAmount;
        private Long maxAmount;
        private BigDecimal minTotal;
        private BigDecimal maxTotal;
        private Set<ParameterSource> sources;
        private Integer createdBy;
        private LocalDateTime createdOn;

        public ParameterBuilder(Integer id, String code, Integer createdBy, LocalDateTime createdOn) {
            this.id = id;
            this.parameterType = ParameterType.findByCode(code);
            this.createdBy = createdBy;
            this.createdOn = createdOn;
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

        public ParameterBuilder reserved(boolean reserved) {
            this.reserved = reserved;
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
