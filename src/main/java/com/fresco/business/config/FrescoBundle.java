package com.fresco.business.config;

import com.zacate.bean.Reflections;
import com.zacate.i18n.BundleAggregator;
import com.zacate.i18n.LocalizedConstants;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author Armando Alaniz
 */
public class FrescoBundle extends BundleAggregator {

    public FrescoBundle() {
        // TODO Add supported languages
        super(Arrays.asList(Package.getPackages())
                .stream()
                .map(Package::getName)
                .filter(name -> name.startsWith("com.fresco.business"))
                .map(Reflections::getParentPackageName)
                .map(name -> new StringBuilder(name)
                        .append(LocalizedConstants.DOT)
                        .append(LocalizedConstants.I18N_PACKAGE_SUFFIX)
                        .toString())
                .collect(Collectors.toList()));
    }

}
