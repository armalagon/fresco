package com.fresco.business.i18n;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public final class LocalizedMessage {

    // TODO Crear parent bundle
    // TODO Crear una clase utilitaria donde se almacenen la referencia a todos los bundles de la app
    private static final String PARENT_RESOURCE_BUNDLE = "com.fresco.business.general.messages";

    private final ResourceBundle bundle;
    private final String key;
    private final Object[] arguments;
    private Object[] translatedArguments;

    private LocalizedMessage(ResourceBundle bundle, String messageKey, Object... arguments) {
        this.bundle = bundle;
        this.key = messageKey;
        this.arguments = arguments;
    }

    public String getKey() {
        return key;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Object[] getTranslatedArguments() {
        return translatedArguments;
    }

    public String getMessage() {
        String template = bundle.getString(key);

        if (template == null) {
            throw new IllegalArgumentException("The key [" + key + "] doesn't exist in the specified bundle [" + bundle.getBaseBundleName() + "]");
        }
        if (arguments == null || arguments.length == 0) {
            return template;
        }

        translatedArguments = new Object[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof LocalizedEnum) {
                translatedArguments[i] = ((LocalizedEnum) arguments[i]).getMessage();
            } else if (arguments[i] instanceof CustomBundleKey) {
                String extractedKey = ((CustomBundleKey) arguments[i]).getFullyQualifiedKeyName();
                translatedArguments[i] = bundle.getString(extractedKey);
            } else if (arguments[i] instanceof String) {
                String argument = (String) arguments[i];

                if (argument.startsWith("{") && argument.endsWith("}")) {
                    String extractedKey = argument.substring(1, argument.length() - 1);
                    translatedArguments[i] = bundle.getString(extractedKey);
                } else {
                    translatedArguments[i] = argument;
                }
            } else {
                translatedArguments[i] = arguments[i];
            }
        }

        return MessageFormat.format(template, translatedArguments);
    }

    public static LocalizedMessage create(String bundleName, String key, Object... arguments) {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
        return new LocalizedMessage(bundle, key, arguments);
    }
}
