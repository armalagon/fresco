package com.fresco.business.parameter.logic;

import static com.fresco.business.jooq.public_.Tables.PARAMETER;
import static com.fresco.business.jooq.public_.Tables.PARAMETER_CONSTRAINT;
import static com.fresco.business.jooq.public_.Tables.PARAMETER_SOURCE;
import com.fresco.business.jooq.public_.tables.records.ParameterSourceRecord;
import com.fresco.business.parameter.model.Parameter;
import com.fresco.business.parameter.model.ParameterSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import javax.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.Result;

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

    public List<Parameter> findAll() {
        Map<Integer, Result<ParameterSourceRecord>> sourcesGroupByParameterId = context.selectFrom(PARAMETER_SOURCE).fetch()
                .intoGroups(PARAMETER_SOURCE.PARAMETER_ID);

        List<Parameter> parameters = context
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
                Result<ParameterSourceRecord> result = sourcesGroupByParameterId.get(record.get(PARAMETER.ID));

                List<ParameterSource> sources;
                if (result == null) {
                    sources = Collections.emptyList();
                } else {
                    sources = result.map(innerRecord -> {
                        return new ParameterSource(innerRecord.get(PARAMETER_SOURCE.ID), innerRecord.get(PARAMETER_SOURCE.CODE),
                                innerRecord.get(PARAMETER_SOURCE.FULLY_QUALIFIED_CLASSNAME), innerRecord.get(PARAMETER_SOURCE.QUERY),
                                innerRecord.get(PARAMETER_SOURCE.SEQUENCE_NUMBER));
                    });
                }

                // TODO Map LocalDate constraints
                return new Parameter.ParameterBuilder(record.get(PARAMETER.ID), record.get(PARAMETER.CODE))
                        .dataType(record.get(PARAMETER.DATA_TYPE_ENUM))
                        .value(record.get(PARAMETER.VALUE))
                        .valueSourceType(record.get(PARAMETER.VALUE_SOURCE_TYPE_ENUM))
                        .unitOfMeasurement(record.get(PARAMETER.UNIT_OF_MEASUREMENT_ENUM))
                        .businessProcessType(record.get(PARAMETER.BUSINESS_PROCESS_TYPE_ENUM))
                        .minAmount(record.get(PARAMETER_CONSTRAINT.MIN_AMOUNT))
                        .maxAmount(record.get(PARAMETER_CONSTRAINT.MAX_AMOUNT))
                        .minDate(null)
                        .maxDate(null)
                        .minTotal(record.get(PARAMETER_CONSTRAINT.MIN_TOTAL))
                        .maxTotal(record.get(PARAMETER_CONSTRAINT.MAX_TOTAL))
                        .addSources(sources)
                        .build();
            });

        return parameters;
    }

}
