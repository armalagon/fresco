package com.fresco.presentation.security;

import java.time.LocalDateTime;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public final class ChangesMadeByEvent {

    private Integer id;
    private LocalDateTime eventTime;

    public ChangesMadeByEvent() {
    }

    public ChangesMadeByEvent(Integer id, LocalDateTime eventTime) {
        this.id = id;
        this.eventTime = eventTime;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return "AuditEvent{" + "id=" + id + ", eventTime=" + eventTime + '}';
    }

}
