package com.childsplay.pawfectly.functions.session.repository;

import com.childsplay.pawfectly.common.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    /**
     * Finds the first session model by the user's master ID.
     *
     * @param userId The master ID of the user.
     * @return An optional containing the session model if found, or an empty optional if not found.
     */
    Optional<Session> findFirstByUserMasterId(Long userId);

    /**
     * Finds the first Session with the given email and token.
     *
     * @param email The email associated with the Session to be found.
     * @param token The token associated with the Session to be found.
     * @return An Optional containing the first Session found with the specified email and token, or an empty Optional if not found.
     */
    Optional<Session> findFirstByEmailAndToken(String email, String token);
    
}
