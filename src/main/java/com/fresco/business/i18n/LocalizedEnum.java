package com.fresco.business.i18n;

import static com.fresco.business.i18n.LocalizedConstants.DOT;

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

        StringBuilder key = new StringBuilder(this.getClass().getName());
        key.append(DOT);
        key.append(((Enum) this).name());
        return getMessage(key.toString());
    }

}
