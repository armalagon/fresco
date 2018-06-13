package com.fresco.business.general.exception;

import com.fresco.business.i18n.BundleIdentifier;
import com.fresco.business.i18n.BundleKeyGenerator;
import static com.fresco.business.i18n.LocalizedConstants.EMPTY_BRACKETS;
import static com.fresco.business.i18n.LocalizedConstants.PREFIX_TO_EXPAND_PARAMETER_KEY;
import com.fresco.business.i18n.LocalizedMessageResolver;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class BusinessException extends Exception implements BundleIdentifier {

    protected String errorCode;
    protected Object[] arguments;

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

    public BusinessException(String errorCode, Object... arguments) {
        this(errorCode, true, arguments);
    }

    public BusinessException(String errorCode, boolean expandKeyBasedOnCurrentClass, Object... arguments) {
        this.errorCode = expandKeyBasedOnCurrentClass ? BundleKeyGenerator.expandKeyUsing(errorCode, this.getClass()) : errorCode;
        this.arguments = doTransform(arguments);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public String getMessage() {
        if (errorCode != null) {
            return LocalizedMessageResolver.translate(getDefaultBaseBundleName(), errorCode, arguments);
        } else {
            return super.getMessage();
        }
    }

    protected Object[] doTransform(Object[] arguments) {
        if (arguments == null || arguments.length == 0) {
            return arguments;
        }

        Object[] argsTransformed = new Object[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            Object arg = arguments[i];
            if (arg instanceof String) {
                String value = (String) arg;
                if (LocalizedMessageResolver.isReservedArgument(value) && value.startsWith(PREFIX_TO_EXPAND_PARAMETER_KEY, 1)) {
                    argsTransformed[i] = value.replace(EMPTY_BRACKETS, this.getClass().getName());
                } else {
                    argsTransformed[i] = arg;
                }
            } else {
                argsTransformed[i] = arg;
            }
        }

        return argsTransformed;
    }

}
