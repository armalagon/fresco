package com.fresco.presentation.security;

import com.fresco.business.security.model.User;
import java.time.LocalDateTime;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class ChangesMadeByDTO {

    private User user;
    private LocalDateTime auditTime;

    public ChangesMadeByDTO() {
    }

    public ChangesMadeByDTO(User user, LocalDateTime auditTime) {
        this.user = user;
        this.auditTime = auditTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    @Override
    public String toString() {
        return "ChangesMadeByDTO{" + "user=" + user + ", auditTime=" + auditTime + '}';
    }

}
