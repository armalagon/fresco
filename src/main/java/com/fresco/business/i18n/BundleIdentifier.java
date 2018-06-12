package com.fresco.business.i18n;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface BundleIdentifier {

    String STANDARD_BUNDLE_FILENAME = "messages";
    String DOT = ".";
    String I18N_PACKAGE_SUFFIX = "i18n";

    default String getBundlePrefix() {
        String packageName = this.getClass().getPackage().getName();
        StringBuilder i18n = new StringBuilder();

        i18n.append(packageName.substring(0, packageName.lastIndexOf(DOT) + 1));
        i18n.append(I18N_PACKAGE_SUFFIX);

        return i18n.toString();
    }

    default String getBundleFilename() {
        return STANDARD_BUNDLE_FILENAME;
    }

    default String getDefaultBaseBundleName() {
        String bundlePrefix = getBundlePrefix();
        StringBuilder baseName = new StringBuilder(bundlePrefix);

        if (!bundlePrefix.endsWith(DOT)) {
            baseName.append(DOT);
        }
        baseName.append(getBundleFilename());
        return baseName.toString();
    }

}
