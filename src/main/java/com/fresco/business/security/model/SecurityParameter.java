package com.fresco.business.security.model;

import com.fresco.business.general.model.BusinessProcess;
import com.fresco.business.parameter.model.Parameter;
import com.fresco.business.parameter.model.ParameterBuilder;
import com.fresco.business.parameter.model.ParameterValueSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public enum SecurityParameter implements Parameter {

    MINIMUM_PASSWORD_LENGTH(ParameterBuilder.create("minimumPasswordLength", "Minimum Password Length", ValueSourceType.SIMPLE_VALUE,
            SecurityProcess.AUTHENTICATION).minAmount(7L)) {

        @Override
        public String getDataType() {
            return getValue().getClass().getSimpleName();
        }

        @Override
        public Integer getValue() {
            return 7;
        }
    },
    ENFORCE_PASSWORD_HISTORY(ParameterBuilder.create("enforcePasswordHistory", "Enforce Password History", ValueSourceType.SIMPLE_VALUE,
            SecurityProcess.AUTHENTICATION).minAmount().maxAmount(10L)) {

        @Override
        public String getDataType() {
            return getValue().getClass().getSimpleName();
        }

        @Override
        public Integer getValue() {
            return 10;
        }
    },
    PASSWORD_EXPIRATION(ParameterBuilder.create("passwordExpiration", "Password Expiration (In Months)", ValueSourceType.SIMPLE_VALUE,
            SecurityProcess.LOCKOUT_POLICY).minAmount(6L).maxAmount(12L)) {

        @Override
        public String getDataType() {
            return getValue().getClass().getSimpleName();
        }

        @Override
        public Integer getValue() {
            return 6;
        }
    },
    MAXIMUM_DAYS_WITHOUT_USER_ACTIVITY(ParameterBuilder.create("maximunDaysWithoutUserActivity", "Maximun Days Without User Activity",
            ValueSourceType.SIMPLE_VALUE, SecurityProcess.LOCKOUT_POLICY).minAmount(7L).maxAmount(60L)) {

        @Override
        public String getDataType() {
            return getValue().getClass().getSimpleName();
        }

        @Override
        public Integer getValue() {
            return 30;
        }
    },
    MAXIMUM_DAYS_WITHOUT_RESETTING_PASWORD(ParameterBuilder.create("maximunDaysWithoutResettingPassword",
            "Maximun Days Without Resetting Password", ValueSourceType.SIMPLE_VALUE,
            SecurityProcess.LOCKOUT_POLICY).minAmount(1L).maxAmount(30L)) {

        @Override
        public String getDataType() {
            return getValue().getClass().getSimpleName();
        }

        @Override
        public Integer getValue() {
            return 7;
        }
    },
    MAXIMUM_FAILED_LOGIN_ATTEMPTS(ParameterBuilder.create("maximunFailedLoginAttempts", "Maximun Failed Login Attempts",
            ValueSourceType.SIMPLE_VALUE, SecurityProcess.LOCKOUT_POLICY).minAmount(3L).maxAmount(10L)) {

        @Override
        public String getDataType() {
            return getValue().getClass().getSimpleName();
        }

        @Override
        public Integer getValue() {
            return 5;
        }
    };

    private final String code;
    private final String description;
    private final ValueSourceType valueSourceType;
    private final BusinessProcess process;
    private final Set<ParameterValueSource> valueSources;
    private final Long minAmount;
    private final Long maxAmount;
    private final LocalDate minDate;
    private final LocalDate maxDate;
    private final BigDecimal minTotalAmount;
    private final BigDecimal maxTotalAmount;

    SecurityParameter(ParameterBuilder parameterBuilder) {
       this.code = parameterBuilder.getCode();
       this.description = parameterBuilder.getDescription();
       this.valueSourceType = parameterBuilder.getValueSourceType();
       this.process = parameterBuilder.getProcess();
       this.valueSources = parameterBuilder.getValueSources();
       this.minAmount = parameterBuilder.getMinAmount();
       this.maxAmount = parameterBuilder.getMaxAmount();
       this.minDate = parameterBuilder.getMinDate();
       this.maxDate = parameterBuilder.getMaxDate();
       this.minTotalAmount = parameterBuilder.getMinTotalAmount();
       this.maxTotalAmount = parameterBuilder.getMaxTotalAmount();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public ValueSourceType getValueSourceType() {
        return valueSourceType;
    }

    @Override
    public BusinessProcess getProcess() {
        return process;
    }

    @Override
    public Set<ParameterValueSource> getValueSources() {
        return Collections.unmodifiableSet(valueSources);
    }

    @Override
    public Long getMinAmount() {
        return minAmount;
    }

    @Override
    public Long getMaxAmount() {
        return maxAmount;
    }

    @Override
    public LocalDate getMinDate() {
        return minDate;
    }

    @Override
    public LocalDate getMaxDate() {
        return maxDate;
    }

    @Override
    public BigDecimal getMinTotalAmount() {
        return minTotalAmount;
    }

    @Override
    public BigDecimal getMaxTotalAmount() {
        return maxTotalAmount;
    }
}
