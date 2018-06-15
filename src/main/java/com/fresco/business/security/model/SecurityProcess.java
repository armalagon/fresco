package com.fresco.business.security.model;

import com.fresco.business.general.model.BusinessProcess;
import com.zacate.i18n.LocalizedEnum;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public enum SecurityProcess implements BusinessProcess, LocalizedEnum {

    SECURITY(null),
    AUTHENTICATION(SECURITY),
    LOCKOUT_POLICY(SECURITY);

    private final BusinessProcess parentProcess;

    SecurityProcess(BusinessProcess parentProcess) {
        this.parentProcess = parentProcess;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public BusinessProcess getParentProcess() {
        return parentProcess;
    }

}
