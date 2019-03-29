package com.mindera.login.models.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sessionAuthToken;
    private LocalDateTime expiryDate;

    public Session() {
    }

    public Session(String sessionAuthToken, LocalDateTime expiryDate) {
        this.sessionAuthToken = sessionAuthToken;
        this.expiryDate = expiryDate;
    }

    public String getSessionAuthToken() {
        return sessionAuthToken;
    }

    public void setSessionAuthToken(String sessionAuthToken) {
        this.sessionAuthToken = sessionAuthToken;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

}