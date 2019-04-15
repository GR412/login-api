package com.mindera.login.repositories;

import com.mindera.login.models.database.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This interface provides a means of accessing methods to perform CRUD operations on the Session database table.
 *
 * It extends the JpaRepository so we can make use of existing methods rather than writing our own from scratch.
 */
@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

    /**
     * Interface signature that finds a session token in the Session table by the session auth token code and the
     * expiry date.
     *
     * @param token the supplied token of the session auth token code you want to find in the Session table.
     * @param expiryDate the supplied expiry date of the session auth token you want to find in the Session table.
     *
     * @return an optional Session if the session auth token code is found and the expiry date matches the supplied one,
     * otherwise return an empty Optional if no session auth token is found.
     */
    Optional<Session> findBySessionAuthTokenAndExpiryDateAfter(String token, LocalDateTime expiryDate);

    Optional<Session> findBySessionAuthToken(String token);

}