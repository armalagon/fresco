package com.fresco.business.general.model;

import com.zacate.identifier.StringNaturalIdentifierLocalizable;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface BusinessProcess extends StringNaturalIdentifierLocalizable {

    BusinessProcess getParentProcess();

    BusinessCategory getCategory();

}
