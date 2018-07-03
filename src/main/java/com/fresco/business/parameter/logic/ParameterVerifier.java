package com.fresco.business.parameter.logic;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
@Singleton
@Startup
public class ParameterVerifier {

    @PostConstruct
    public void validateOnStartup() {

    }

}
