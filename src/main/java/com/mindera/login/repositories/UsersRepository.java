package com.mindera.login.repositories;

import com.mindera.login.models.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface provides a means of accessing methods to perform CRUD operations on the Users database table.
 *
 * It extends the JpaRepository so we can make use of existing methods rather than writing our own from scratch.
 *
 * Although in this case we need a specific method to find a User by username so we define it's method signature here.
 */

@Repository
public interface UsersRepository extends JpaRepository<User, Integer>
{

    /**
     * Interface signature that finds a User in the Users table by username.
     *
     * @param username the supplied username of the user you want to find in the Users table.
     * @return an optional User if the username is found, otherwise return an empty Optional if no User is
     * found.
     */
    Optional<User> findByUsername(String username);
}
