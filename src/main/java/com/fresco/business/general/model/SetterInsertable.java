package com.fresco.business.general.model;

import java.time.LocalDateTime;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface SetterInsertable {

    void setCreatedBy(Integer createdBy);

    void setCreatedOn(LocalDateTime createdOn);

}
