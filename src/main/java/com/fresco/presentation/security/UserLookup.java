package com.fresco.presentation.security;

import com.fresco.business.security.dto.ChangesMadeByDTO;
import com.fresco.business.security.logic.SecurityProvider;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
@RequestScoped
public class UserLookup {

    @Inject
    SecurityProvider security;

    @Produces
    @Created
    @Model
    ChangesMadeByDTO creation;

    @Produces
    @Updated
    @Model
    ChangesMadeByDTO modification;

    @PostConstruct
    public void init() {
        creation = new ChangesMadeByDTO();
        modification = new ChangesMadeByDTO();
    }

    public void creation(@Observes @Created ChangesMadeByEvent event) {
        creation = new ChangesMadeByDTO(security.findUserById(event.getId()), event.getEventTime());
    }

    public void modification(@Observes @Updated ChangesMadeByEvent event) {
        modification = new ChangesMadeByDTO(security.findUserById(event.getId()), event.getEventTime());
    }

}
