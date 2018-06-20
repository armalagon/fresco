package com.fresco.business.parameter.model;

import com.zacate.identifier.EnumLookup;
import com.zacate.identifier.StringNaturalIdentifierLocalizable;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public enum ValueSourceType implements StringNaturalIdentifierLocalizable {

    SIMPLE_VALUE, ENUM, ENTITY, QUERY;

    @Override
    public String getCode() {
        return name();
    }

    public static ValueSourceType findByCode(String code) {
        return EnumLookup.findByCode(ValueSourceType.class, code);
    }

}
