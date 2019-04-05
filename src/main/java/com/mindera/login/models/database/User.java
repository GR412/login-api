package com.mindera.login.models.database;

import javax.persistence.*;

/**
 * This class defines the shape of a User which is composed of an id, username, email and password.
 *
 * The User table is also mapped to this class through object relational mapping.
 */
@Entity //Mark this as an Entity as this class is used in ORM to the Users database table.
public class User {
    @Id //Mark id as a database id.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Defines the way an id is automatically generated.
    private int id;
    private String username;
    private String email;
    private String password;

    /*To make this association bidirectional with the Session entity, all we’ll have to do is to define the referencing
      (User) entity side via the @OneToOne annotation with the mappedBy attribute.

      We can assign the non-owning entity name to the mappedBy attribute to reference the non-owning (User) entity.*/

    @OneToOne(mappedBy = "user")
    private Session session; //This is a Session object that represents the Session table in the One-One relationship to the Users table.

    //Standard get and set methods.

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Session getSession() {
        return session;
    }

    public void setSession() {
        this.session = session;
    }
}