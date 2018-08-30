package com.fresco.business.security.logic;

import com.fresco.business.general.model.Auditable;
import static com.fresco.business.jooq.security.tables.ApplicationUserTable.APPLICATION_USER;
import com.fresco.business.security.model.User;
import java.time.LocalDateTime;
import java.util.Objects;
import org.jooq.Record;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class SecurityEntitiesMapper {

    protected static final String FIELD_CREATED_BY = "created_by";
    protected static final String FIELD_CREATED_ON = "created_on";
    protected static final String FIELD_UPDATED_BY = "updated_by";
    protected static final String FIELD_UPDATED_ON = "updated_on";

    public static void mapSecurityAttrInto(final Auditable auditable, final Record record) {
        try {
            auditable.setCreatedBy(record.get(FIELD_CREATED_BY, Integer.class));
        } catch (IllegalArgumentException noField) {
        }
        try {
            auditable.setCreatedOn(record.get(FIELD_CREATED_ON, LocalDateTime.class));
        } catch (IllegalArgumentException noField) {
        }
        try {
            auditable.setUpdatedBy(record.get(FIELD_UPDATED_BY, Integer.class));
        } catch (IllegalArgumentException noField) {
        }
        try {
            auditable.setUpdatedOn(record.get(FIELD_UPDATED_ON, LocalDateTime.class));
        } catch (IllegalArgumentException noField) {
        }
    }

    public static User mapUserFrom(final Record record) {
        Objects.requireNonNull(record, "record");
        User user = new User(record.get(APPLICATION_USER.ID), record.get(APPLICATION_USER.LOGIN));
        user.setPassword(record.get(APPLICATION_USER.PASSWORD));
        user.setFullname(record.get(APPLICATION_USER.FULLNAME));
        user.setEmail(record.get(APPLICATION_USER.EMAIL));
        user.setFailedLoginAttempts(record.get(APPLICATION_USER.FAILED_LOGIN_ATTEMPTS));
        user.setFirstFailedLoginAttemptDate(record.get(APPLICATION_USER.FIRST_FAILED_LOGIN_ATTEMPT_DATE));
        user.setIsActive(record.get(APPLICATION_USER.IS_ACTIVE));
        user.setIsLocked(record.get(APPLICATION_USER.IS_LOCKED));
        user.setIsSuperUser(record.get(APPLICATION_USER.IS_SUPER_USER));
        user.setLastLoginDate(record.get(APPLICATION_USER.LAST_LOGIN_DATE));
        user.setCredentialExpire(record.get(APPLICATION_USER.CREDENTIAL_EXPIRE));
        user.setCredentialExpirationDate(record.get(APPLICATION_USER.CREDENTIAL_EXPIRATION_DATE));
        mapSecurityAttrInto(user, record);
        return user;
    }

}
