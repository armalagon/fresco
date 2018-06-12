package com.fresco.business.general.model;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface BusinessProcess extends NaturalIdentifier<String> {

    BusinessProcess getParentProcess();

}
