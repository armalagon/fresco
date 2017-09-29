package com.fresco.business.security.model;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public class User {

    private String username;
    private String password;

    public User() {
    }

    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
        return "User{" + "username=" + username + ", password=" + password + '}';
    }

    public static User of(String username, String password) {
        return new User(username, password);
    }
}
