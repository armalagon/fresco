package com.fresco.business.parameter.logic;

import com.fresco.business.general.model.BusinessProcessType;
import static com.fresco.business.jooq.public_.Tables.PARAMETER;
import static com.fresco.business.jooq.public_.Tables.PARAMETER_CONSTRAINT;
import com.fresco.business.parameter.model.Parameter;
import com.fresco.business.parameter.model.ParameterType;
import com.fresco.business.parameter.model.UnitOfMeasurement;
import com.fresco.business.parameter.model.ValueSourceType;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.jooq.DSLContext;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
@Stateless
public class ParameterProvider {

    @Inject
    DSLContext query;

    public List<Parameter> findAll() {
        return query.select(
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
                ParameterType parameterType = ParameterType.findByCode(record.get(PARAMETER.CODE));
                ValueSourceType valueSourceType = ValueSourceType.findByCode(record.get(PARAMETER.VALUE_SOURCE_TYPE_ENUM));
                UnitOfMeasurement unitOfMeasurement = UnitOfMeasurement.findByCode(record.get(PARAMETER.UNIT_OF_MEASUREMENT_ENUM));
                BusinessProcessType process = BusinessProcessType.findByCode(record.get(PARAMETER.BUSINESS_PROCESS_TYPE_ENUM));

                return new Parameter(record.get(PARAMETER.ID), parameterType, record.get(PARAMETER.DATA_TYPE_ENUM),
                        record.get(PARAMETER.VALUE), valueSourceType, unitOfMeasurement, process,
                        record.get(PARAMETER_CONSTRAINT.MIN_AMOUNT), record.get(PARAMETER_CONSTRAINT.MAX_AMOUNT), null, null,
                        record.get(PARAMETER_CONSTRAINT.MIN_TOTAL), record.get(PARAMETER_CONSTRAINT.MAX_TOTAL));
            });
    }

}
