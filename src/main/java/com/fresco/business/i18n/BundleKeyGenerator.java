package com.fresco.business.i18n;

import static com.fresco.business.i18n.LocalizedConstants.DOT;
import static com.fresco.business.i18n.LocalizedConstants.LEFT_BRACE;
import static com.fresco.business.i18n.LocalizedConstants.RIGHT_BRACE;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class BundleKeyGenerator {

    public static String expandKeyUsing(final String key, final Class<?> clazz) {
        final StringBuilder fqkn = new StringBuilder(clazz.getName());
        fqkn.append(DOT);
        fqkn.append(key);
        return fqkn.toString();
    }

    public static String expandKeyAsReservedParameter(final String key, final Class<?> clazz) {
        final String fqkn = expandKeyUsing(key, clazz);
        final StringBuilder rkn = new StringBuilder(fqkn.length() + 2);
        rkn.append(LEFT_BRACE);
        rkn.append(fqkn);
        rkn.append(RIGHT_BRACE);
        return rkn.toString();
    }

}
