package com.mindera.login.repositories;

import com.mindera.login.models.database.Session;
import com.mindera.login.models.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface provides a means of accessing methods to perform CRUD operations on the Session database table.
 *
 * It extends the JpaRepository so we can make use of existing methods rather than writing our own from scratch.
 */

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer>
{

    /**
     * Interface signature that finds a session token in the Session table by the session auth token code.
     *
     * @param token the supplied token of the session auth token code you want to find in the Session table.
     * @return an optional Session if the session auth token code is found, otherwise return an empty Optional if no session auth token is
     * found.
     */
    Optional<Session> findBySessionAuthToken(String token);
}