package com.fresco.business.security.model;

import com.fresco.business.general.model.Auditable;
import com.zacate.identifier.WriteableIntegerAndStringNaturalIdentifier;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public class User extends WriteableIntegerAndStringNaturalIdentifier implements Auditable {

    @NotNull
    @Size(max = 15)
    private String login;

    @NotNull
    private String password;

    @NotNull
    @Size(max = 80)
    private String fullname;

    @Size(max = 15)
    private String email;

    private int failedLoginAttempts;
    private LocalDateTime firstFailedLoginAttemptDate;
    private boolean isActive;
    private boolean isLocked;
    private boolean isSuperUser;
    private LocalDateTime lastLoginDate;
    private boolean credentialExpire;
    private LocalDateTime credentialExpirationDate;

    @NotNull
    private Integer createdBy;

    @NotNull
    private LocalDateTime createdOn;

    private Integer updatedBy;
    private LocalDateTime updatedOn;

    public User() {
    }

    public User(Integer id, String login) {
        super(id);
        this.login = login;
    }

    public User(String login) {
        this.login = login;
    }

    @Override
    public String getCode() {
        return this.login;
    }

    @Override
    public void setCode(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public LocalDateTime getFirstFailedLoginAttemptDate() {
        return firstFailedLoginAttemptDate;
    }

    public void setFirstFailedLoginAttemptDate(LocalDateTime firstFailedLoginAttemptDate) {
        this.firstFailedLoginAttemptDate = firstFailedLoginAttemptDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean getIsSuperUser() {
        return isSuperUser;
    }

    public void setIsSuperUser(boolean isSuperUser) {
        this.isSuperUser = isSuperUser;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public boolean isCredentialExpire() {
        return credentialExpire;
    }

    public void setCredentialExpire(boolean credentialExpire) {
        this.credentialExpire = credentialExpire;
    }

    public LocalDateTime getCredentialExpirationDate() {
        return credentialExpirationDate;
    }

    public void setCredentialExpirationDate(LocalDateTime credentialExpirationDate) {
        this.credentialExpirationDate = credentialExpirationDate;
    }

    @Override
    public Integer getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @Override
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    @Override
    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "User{" + "login=" + login + ", fullname=" + fullname + ", isActive=" + isActive + ", isLocked=" + isLocked +
                ", isSuperUser=" + isSuperUser + '}';
    }

}
