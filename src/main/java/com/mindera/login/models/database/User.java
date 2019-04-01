package com.mindera.login.models.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class defines the shape of a User which is composed of an id, username, email and password.
 *
 * The User table is also mapped to this class through object relational mapping.
 */
@Entity //Mark this as an Entity as this class is used in ORM to the Users database table.
public class User
{
    @Id //Mark id as a database id.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Defines the way an id is automatically generated.
    private int id;
    private String username, email, password;

    //Standard get and set methods.

    public int getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}