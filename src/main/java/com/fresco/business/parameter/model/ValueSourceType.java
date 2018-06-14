package com.fresco.business.parameter.model;

import com.fresco.business.general.model.NaturalIdentifier;
import com.fresco.business.i18n.LocalizedEnum;

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
