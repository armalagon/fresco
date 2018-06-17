package com.fresco.business.parameter.logic;

import com.fresco.business.general.model.BusinessProcess;
import static com.fresco.business.jooq.public_.Tables.PARAMETER;
import static com.fresco.business.jooq.public_.Tables.PARAMETER_CONSTRAINT;
import static com.fresco.business.jooq.public_.Tables.PROCESS;
import com.fresco.business.parameter.model.Parameter;
import com.fresco.business.parameter.model.UnitOfMeasurement;
import com.fresco.business.parameter.model.ValueSourceType;
import com.zacate.identifier.EnumLookup;
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
                PROCESS.CODE,
                PARAMETER.ID,
                PARAMETER.CODE,
                PARAMETER.DATA_TYPE_ENUM,
                PARAMETER.VALUE,
                PARAMETER.VALUE_SOURCE_TYPE_ENUM,
                PARAMETER.UNIT_OF_MEASUREMENT_ENUM,
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
            .join(PROCESS)
            .on(PARAMETER.PROCESS_ID.eq(PROCESS.ID))
            .fetch(record -> {
                ValueSourceType valueSourceType = EnumLookup.findByCode(ValueSourceType.class, record.get(PARAMETER.VALUE_SOURCE_TYPE_ENUM));
                UnitOfMeasurement unitOfMeasurement = EnumLookup.findByCode(UnitOfMeasurement.class, record.get(PARAMETER.UNIT_OF_MEASUREMENT_ENUM));
                BusinessProcess process = null;

                return new Parameter(record.get(PARAMETER.ID), record.get(PARAMETER.CODE), record.get(PARAMETER.DATA_TYPE_ENUM),
                        record.get(PARAMETER.VALUE), valueSourceType, unitOfMeasurement, process,
                        record.get(PARAMETER_CONSTRAINT.MIN_AMOUNT), record.get(PARAMETER_CONSTRAINT.MAX_AMOUNT), null, null,
                        record.get(PARAMETER_CONSTRAINT.MIN_TOTAL), record.get(PARAMETER_CONSTRAINT.MAX_TOTAL));
            });
    }

}
