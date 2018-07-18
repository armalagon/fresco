package com.fresco.business.general.model;

import java.time.LocalDateTime;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface Updatable {

    Integer getUpdatedBy();

    void setUpdatedBy(Integer updatedBy);

    LocalDateTime getUpdatedOn();

    void setUpdatedOn(LocalDateTime updatedOn);

}
