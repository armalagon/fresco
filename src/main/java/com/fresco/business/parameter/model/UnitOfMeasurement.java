package com.fresco.business.parameter.model;

import com.zacate.i18n.LocalizedEnum;
import com.zacate.identifier.NaturalIdentifier;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public enum UnitOfMeasurement implements NaturalIdentifier<String>, LocalizedEnum {

    DAY, MONTH, N_TIMES, QUANTITY, CHARACTER;

    @Override
    public String getCode() {
        return name();
    }

}
