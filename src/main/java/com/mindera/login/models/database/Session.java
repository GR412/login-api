package com.mindera.login.models.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * This class defines the shape of a Session which is composed of an id, sessionAuthToken and an expiry date.
 *
 * The Session table is also mapped to this class through object relational mapping.
 */

@Entity //Mark this as an Entity as this class is used in ORM to the Users database table.
public class Session {

    @Id //Mark id as a database id.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Defines the way an id is automatically generated.
    private int id;
    private String sessionAuthToken;
    private LocalDateTime expiryDate;

    /**
     * Constructor that assigns the class fields to the value passed in when an instance of this class is created.
     *
     * @param sessionAuthToken the supplied sessionAuthToken
     * @param expiryDate the supplied expiryDate
     */

    public Session(String sessionAuthToken, LocalDateTime expiryDate)
    {
        this.sessionAuthToken = sessionAuthToken;
        this.expiryDate = expiryDate;
    }

    //Standard get and set methods.

    public int getId()
    {
        return id;
    }

    public String getSessionAuthToken()
    {
        return sessionAuthToken;
    }

    public void setSessionAuthToken(String sessionAuthToken)
    {
        this.sessionAuthToken = sessionAuthToken;
    }

    public LocalDateTime getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate)
    {
        this.expiryDate = expiryDate;
    }
}