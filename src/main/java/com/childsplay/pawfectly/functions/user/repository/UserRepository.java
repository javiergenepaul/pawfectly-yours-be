package com.childsplay.pawfectly.functions.user.repository;

import com.childsplay.pawfectly.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return An Optional containing the UserModel if found, or an empty Optional if not found.
     */
    Optional<User> findByEmail(String email);

}
