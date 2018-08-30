package com.fresco.business.config;

import com.zacate.bean.Reflections;
import com.zacate.i18n.BundleAggregator;
import com.zacate.i18n.LocalizedConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Armando Alaniz
 */
public class FrescoBundle extends BundleAggregator {

    public FrescoBundle() {
        // TODO Add supported languages
        super(findI18nPackages());
    }

    private static List<String> findI18nPackages() {
        List<Package> basePackages = Arrays.asList(Package.getPackages());
        List<String> backEndPackages = basePackages.stream()
                .map(Package::getName)
                .filter(name -> name.startsWith("com.fresco.business"))
                .map(Reflections::getParentPackageName)
                .map(name -> new StringBuilder(name)
                        .append(LocalizedConstants.DOT)
                        .append(LocalizedConstants.I18N_PACKAGE_SUFFIX)
                        .toString())
                .collect(Collectors.toList());
        List<String> frontEndPackages = basePackages.stream()
                .map(Package::getName)
                .filter(name -> name.startsWith("com.fresco.presentation"))
                .collect(Collectors.toList());
        List<String> packages = new ArrayList<>(backEndPackages.size() + frontEndPackages.size());
        packages.addAll(backEndPackages);
        packages.addAll(frontEndPackages);
        return packages;
    }

}
