package com.fresco.presentation.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
@Named
@RequestScoped
public class Credentials {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Credentials{" + "username=" + username + ", password=" + password + '}';
    }
}
