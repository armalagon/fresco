package com.fresco.business.parameter.model;

import com.zacate.identifier.EnumLookup;
import com.zacate.identifier.StringNaturalIdentifierLocalizable;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public enum ParameterType implements StringNaturalIdentifierLocalizable {

    // -------------------------------------------------------------------------------------------------------------------------------------
    // SECURITY MODULE
    // -------------------------------------------------------------------------------------------------------------------------------------
    MINIMUM_PASSWORD_LENGTH,
    ENFORCE_PASSWORD_HISTORY,
    PASSWORD_EXPIRATION,
    MAXIMUM_DAYS_WITHOUT_USER_ACTIVITY,
    MAXIMUM_DAYS_WITHOUT_RESETTING_PASWORD,
    MAXIMUM_FAILED_LOGIN_ATTEMPTS;

    @Override
    public String getCode() {
        return name();
    }

    public static ParameterType findByCode(String code) {
        return EnumLookup.findByCode(ParameterType.class, code);
    }

}
