package com.fresco.presentation.security;

import java.time.LocalDateTime;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class AuditEvent {

    private Integer userId;
    private LocalDateTime auditTime;

    public AuditEvent() {
    }

    public AuditEvent(Integer userId, LocalDateTime auditTime) {
        this.userId = userId;
        this.auditTime = auditTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    @Override
    public String toString() {
        return "AuditEvent{" + "userId=" + userId + ", auditTime=" + auditTime + '}';
    }

}
