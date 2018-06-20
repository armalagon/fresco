package com.fresco.business.parameter.logic;

import static com.fresco.business.jooq.public_.Tables.PARAMETER;
import static com.fresco.business.jooq.public_.Tables.PARAMETER_CONSTRAINT;
import static com.fresco.business.jooq.public_.Tables.PARAMETER_SOURCE;
import com.fresco.business.parameter.model.Parameter;
import com.fresco.business.parameter.model.ParameterSource;
import com.fresco.business.parameter.model.ParameterType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import org.jooq.DSLContext;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
@Singleton
public class ParameterProvider {

    @Inject
    DSLContext context;

    private Map<String, Parameter> parameters;

    private List<Parameter> loadAll() {
        Map<Integer, List<ParameterSource>> sourcesByParameterId = context
                .selectFrom(PARAMETER_SOURCE)
                .fetch(record -> {
                    return new ParameterSource(record.get(PARAMETER_SOURCE.ID), record.get(PARAMETER_SOURCE.CODE),
                            record.get(PARAMETER_SOURCE.PARAMETER_ID), record.get(PARAMETER_SOURCE.FULLY_QUALIFIED_CLASSNAME),
                            record.get(PARAMETER_SOURCE.QUERY), record.get(PARAMETER_SOURCE.SEQUENCE_NUMBER));
                })
                .stream()
                .collect(Collectors.groupingBy(ParameterSource::getParameterId));

        return context
            .select(
                PARAMETER.ID,
                PARAMETER.CODE,
                PARAMETER.DATA_TYPE_ENUM,
                PARAMETER.VALUE,
                PARAMETER.VALUE_SOURCE_TYPE_ENUM,
                PARAMETER.UNIT_OF_MEASUREMENT_ENUM,
                PARAMETER.BUSINESS_PROCESS_TYPE_ENUM,
                PARAMETER_CONSTRAINT.MIN_AMOUNT,
                PARAMETER_CONSTRAINT.MAX_AMOUNT,
                PARAMETER_CONSTRAINT.MIN_DATE,
                PARAMETER_CONSTRAINT.MAX_DATE,
                PARAMETER_CONSTRAINT.MIN_TOTAL,
                PARAMETER_CONSTRAINT.MAX_TOTAL
            )
            .from(PARAMETER)
            .leftJoin(PARAMETER_CONSTRAINT)
            .on(PARAMETER.ID.eq(PARAMETER_CONSTRAINT.ID))
            .fetch(record -> {
                return new Parameter.ParameterBuilder(record.get(PARAMETER.ID), record.get(PARAMETER.CODE))
                        .dataType(record.get(PARAMETER.DATA_TYPE_ENUM))
                        .value(record.get(PARAMETER.VALUE))
                        .valueSourceType(record.get(PARAMETER.VALUE_SOURCE_TYPE_ENUM))
                        .unitOfMeasurement(record.get(PARAMETER.UNIT_OF_MEASUREMENT_ENUM))
                        .businessProcessType(record.get(PARAMETER.BUSINESS_PROCESS_TYPE_ENUM))
                        .minAmount(record.get(PARAMETER_CONSTRAINT.MIN_AMOUNT))
                        .maxAmount(record.get(PARAMETER_CONSTRAINT.MAX_AMOUNT))
                        .minDate(record.get(PARAMETER_CONSTRAINT.MIN_DATE))
                        .maxDate(record.get(PARAMETER_CONSTRAINT.MAX_DATE))
                        .minTotal(record.get(PARAMETER_CONSTRAINT.MIN_TOTAL))
                        .maxTotal(record.get(PARAMETER_CONSTRAINT.MAX_TOTAL))
                        .addSources(sourcesByParameterId.getOrDefault(record.get(PARAMETER.ID), Collections.emptyList()))
                        .build();
            });
    }

    @PostConstruct
    public void onInit() {
        parameters = loadAll().stream().collect(Collectors.toMap(
                Parameter::getCode,
                Function.identity(),
                (oldValue, newValue) -> oldValue,
                ConcurrentHashMap::new));
    }

    public List<Parameter> findAll() {
        return new ArrayList<>(parameters.values());
    }

    public Optional<Parameter> findById(ParameterType parameterType) {
        return Optional.ofNullable(parameters.get(parameterType.getCode()));
    }

    public List<Parameter> findByText(String value) {
        return parameters.values().stream()
                .filter(p -> p.containsIgnoreCase(value))
                .collect(Collectors.toList());
    }

    public <T> Optional<T> getValue(ParameterType parameterType, Class<T> clazz) {
        // TODO Pendiente
        Parameter parameter = parameters.get(parameterType.getCode());

        if (parameter == null) {
            throw new IllegalArgumentException("There is no parameter with code []");
        }

        return null;
    }

}
