package com.fresco.business.general.exception;

import com.fresco.business.i18n.BundleIdentifier;
import com.fresco.business.i18n.CustomBundleKey;
import com.fresco.business.i18n.LocalizedMessage;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class BusinessException extends Exception implements BundleIdentifier {

    protected CustomBundleKey key;
    protected Object[] arguments;
    protected LocalizedMessage im;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(CustomBundleKey key) {
        this(key, (Object[]) null);
    }

    public BusinessException(CustomBundleKey key, Object... values) {
        this.key = key;
        this.arguments = values;
        this.im = LocalizedMessage.create(getDefaultBaseBundleName(), this.key.getFullyQualifiedKeyName(), this.arguments);
    }

    public String getErrorCode() {
        return key != null ? key.getFullyQualifiedKeyName() : null;
    }

    public Object[] getDefaultArguments() {
        return arguments;
    }

    @Override
    public String getMessage() {
        if (key != null) {
            return im.getMessage();
        } else {
            return super.getMessage();
        }
    }

}
