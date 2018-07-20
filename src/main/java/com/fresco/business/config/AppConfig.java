package com.fresco.business.config;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class AppConfig {

    private final SupportedCountry country;

    public AppConfig(SupportedCountry country) {
        this.country = country;
    }

    public SupportedCountry getCountry() {
        return country;
    }

}
