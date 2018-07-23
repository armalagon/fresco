package com.fresco.business.security.logic;

import static com.fresco.business.jooq.security.tables.ApplicationUserTable.APPLICATION_USER;
import com.fresco.business.security.model.User;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.jooq.DSLContext;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
@Stateless
public class SecurityProvider {

    @Inject
    DSLContext context;

    public User findUserByLogin(final String login) {
        return context
                .selectFrom(APPLICATION_USER)
                .where(APPLICATION_USER.LOGIN.eq(login))
                .fetchOne(SecurityEntitiesMapper::mapUserFrom);
    }

    public User findUserById(final int id) {
        return context
                .selectFrom(APPLICATION_USER)
                .where(APPLICATION_USER.ID.eq(id))
                .fetchOne(SecurityEntitiesMapper::mapUserFrom);
    }

}
