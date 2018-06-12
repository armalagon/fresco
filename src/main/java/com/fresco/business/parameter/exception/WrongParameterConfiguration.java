package com.fresco.business.parameter.exception;

import com.fresco.business.general.exception.BusinessException;
import com.fresco.business.i18n.CustomBundleKey;

/**
 *
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class WrongParameterConfiguration extends BusinessException {

    public WrongParameterConfiguration(String key) {
        this(key, (Object[]) null);
    }

    public WrongParameterConfiguration(String key, Object... values) {
        super(CustomBundleKey.create(key, WrongParameterConfiguration.class), values);
    }

}
