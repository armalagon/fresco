package com.fresco.business.parameter.model;

import com.zacate.identifier.EnumLookup;
import com.zacate.identifier.StringNaturalIdentifierLocalizable;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public enum UnitOfMeasurement implements StringNaturalIdentifierLocalizable {

    DAY, MONTH, N_TIMES, QUANTITY, CHARACTER;

    @Override
    public String getCode() {
        return name();
    }

    public static UnitOfMeasurement findByCode(String code) {
        return EnumLookup.findByCode(UnitOfMeasurement.class, code);
    }

}
