package com.fresco.business.parameter.model;

import com.fresco.business.general.model.NaturalIdentifier;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface ParameterValueSource extends NaturalIdentifier<String> {

    String getFullyQualifiedClassname();

    String getQuery();

    int getSequenceNumber();

}
