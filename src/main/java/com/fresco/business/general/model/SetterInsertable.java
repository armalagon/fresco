package com.fresco.business.general.model;

import java.time.LocalDate;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface SetterInsertable extends GetterInsertable {

    void setCreatedBy(Integer createdBy);

    void setCreatedOn(LocalDate createdOn);

}
