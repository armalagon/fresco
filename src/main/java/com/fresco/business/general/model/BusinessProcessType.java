package com.fresco.business.general.model;

import com.zacate.identifier.EnumLookup;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public enum BusinessProcessType implements BusinessProcess {

    // -------------------------------------------------------------------------------------------------------------------------------------
    // SECURITY MODULE
    // -------------------------------------------------------------------------------------------------------------------------------------
    SECURITY(null, "SECURITY"),
    AUTHENTICATION(SECURITY),
    LOCKOUT_POLICY(SECURITY);

    private static final String NO_CATEGORY_DEFINED = "<CATEGORY UNDEFINED>";

    private final BusinessProcess parentProcess;
    private final String category;

    private BusinessProcessType(BusinessProcess parentProcess) {
        this(parentProcess, null);
    }

    private BusinessProcessType(BusinessProcess parentProcess, String category) {
        this.parentProcess = parentProcess;
        if (category == null) {
            this.category = parentProcess.getCategory() == null ? NO_CATEGORY_DEFINED : parentProcess.getCategory();
        } else {
            this.category = category;
        }
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public BusinessProcess getParentProcess() {
        return parentProcess;
    }

    @Override
    public String getCategory() {
        return category;
    }

    public static BusinessProcessType findByCode(String code) {
        return EnumLookup.findByCode(BusinessProcessType.class, code);
    }

}
