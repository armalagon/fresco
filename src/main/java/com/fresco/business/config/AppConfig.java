package com.fresco.business.config;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class AppConfig {

    private final CountrySupported country;

    public AppConfig(CountrySupported country) {
        this.country = country;
    }

    public CountrySupported getCountry() {
        return country;
    }

}
