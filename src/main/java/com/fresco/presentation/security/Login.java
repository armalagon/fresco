package com.fresco.presentation.security;

import com.fresco.business.security.model.User;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
@Named
@SessionScoped
public class Login implements Serializable {

    @Inject
    Credentials credentials;

    private User user;

    public void login() {
        if ("fresco".equals(credentials.getUsername()) && "fresco123".equals(credentials.getPassword())) {
            user = User.of(credentials.getUsername(), credentials.getPassword());
        } else {

        }
    }

    public void logout() {
        user = null;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    @Produces
    @LoggedIn
    public User getLoggedInUser() {
        return user;
    }
}
