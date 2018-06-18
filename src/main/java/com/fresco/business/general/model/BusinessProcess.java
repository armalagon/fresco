package com.fresco.business.general.model;

import com.zacate.i18n.LocalizedEnum;
import com.zacate.identifier.NaturalIdentifier;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface BusinessProcess extends NaturalIdentifier<String>, LocalizedEnum {

    BusinessProcess getParentProcess();

    String getCategory();

}
