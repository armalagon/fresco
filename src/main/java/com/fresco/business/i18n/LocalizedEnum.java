package com.fresco.business.i18n;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface LocalizedEnum extends Localized {

    default String getMessage() {
        if (!(this instanceof Enum)) {
            throw new UnsupportedOperationException("The class [" + this.getClass().getSimpleName() + "] must be an Enum type to be able to retrieve the description");
        }

        String key = ((Enum) this).name();
        return getMessage(key);
    }

}
