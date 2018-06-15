package com.fresco.business.parameter.logic;

import com.fresco.business.parameter.exception.WrongParameterConfiguration;
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
public class ParameterConfigChecker {

    @PostConstruct
    public void validateConfigOnStartup() {

    }

    public void validateConstraintConfig() throws WrongParameterConfiguration {

    }

}
