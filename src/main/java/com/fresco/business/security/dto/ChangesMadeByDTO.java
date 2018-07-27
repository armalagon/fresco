package com.fresco.business.security.dto;

import com.fresco.business.security.model.User;
import java.time.LocalDateTime;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public final class ChangesMadeByDTO {

    private User user;
    private LocalDateTime eventTime;

    public ChangesMadeByDTO() {
    }

    public ChangesMadeByDTO(User user, LocalDateTime eventTime) {
        this.user = user;
        this.eventTime = eventTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return "ChangesMadeByDTO{" + "user=" + user + ", eventTime=" + eventTime + '}';
    }

}
