package com.fresco.business.i18n;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public final class CustomBundleKey {

    private static final String DOT = ".";

    private final String fullyQualifiedKeyName;

    private CustomBundleKey(final String key, final Class<?> clazz) {
        final StringBuilder tmp = new StringBuilder();
        final String classnameAsPrefix = clazz.getSimpleName();

        if (!key.startsWith(classnameAsPrefix)) {
            tmp.append(classnameAsPrefix).append(DOT);
        }
        tmp.append(key);

        fullyQualifiedKeyName = tmp.toString();
    }

    public String getFullyQualifiedKeyName() {
        return fullyQualifiedKeyName;
    }

    public static CustomBundleKey create(final String key, final Class<?> clazz) {
        return new CustomBundleKey(key, clazz);
    }

}
