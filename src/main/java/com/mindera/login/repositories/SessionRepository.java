package com.mindera.login.repositories;

import com.mindera.login.models.database.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface provides a means of accessing methods to perform CRUD operations on the Session database table.
 *
 * It extends the JpaRepository so we can make use of existing methods rather than writing our own from scratch.
 */

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer>
{

}