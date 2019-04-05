package com.mindera.login.models.database;

import javax.persistence.*;
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

    /*The @JoinColumn annotation configures the name of the column in the Session table that maps to the primary key in
      the User table. If we don’t provide a name, then Hibernate will follow some rules to select a default one.

      We only need it on the owning side of the foreign key relationship. Whoever owns the foreign key column gets the
      @JoinColumn annotation.*/

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; //This is a User object that represents the User table in the One-One relationship to the Session table.

    /**
     * Constructor that assigns the class fields to the value passed in when an instance of this class is created.
     *
     * @param sessionAuthToken the supplied sessionAuthToken
     * @param expiryDate the supplied expiryDate
     */

    public Session(String sessionAuthToken, LocalDateTime expiryDate) {
        this.sessionAuthToken = sessionAuthToken;
        this.expiryDate = expiryDate;
    }

    //Standard get and set methods.

    public int getId() {
        return id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}