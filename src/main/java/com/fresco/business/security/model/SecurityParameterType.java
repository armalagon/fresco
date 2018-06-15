package com.fresco.business.security.model;

import com.zacate.i18n.LocalizedEnum;
import com.zacate.identifier.NaturalIdentifier;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public enum SecurityParameterType implements NaturalIdentifier<String>, LocalizedEnum {

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

}
