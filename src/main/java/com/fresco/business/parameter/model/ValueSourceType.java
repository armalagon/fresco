package com.fresco.business.parameter.model;

import com.zacate.i18n.LocalizedEnum;
import com.zacate.identifier.NaturalIdentifier;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public enum ValueSourceType implements NaturalIdentifier<String>, LocalizedEnum {

    SIMPLE_VALUE, ENUM, ENTITY, QUERY;

    @Override
    public String getCode() {
        return name();
    }

}
