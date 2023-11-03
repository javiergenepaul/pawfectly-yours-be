package com.childsplay.pawfectly.functions.loginprovider.repository;

import com.childsplay.pawfectly.common.model.LoginProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginProviderRepository extends JpaRepository<LoginProvider, Long> {

    /**
     * Spring Data JPA repository method for retrieving a LoginProvider entity by user ID and provider name.
     *
     * @param userId         The user ID for which the LoginProvider is associated.
     * @param providerName   The name of the third-party provider.
     * @return An Optional containing the LoginProvider entity if found, or an empty Optional if not found.
     */
    @Query(value
            = "SELECT * "
            + "FROM LoginProvider "
            + "WHERE userId = :userId "
            + "AND providerName = :providerName "
            + "AND isDeleted = false "
            + "LIMIT 1",
            nativeQuery = true)
    Optional<LoginProvider> findByUserIdAndProviderName(@Param("userId") long userId,
                                             @Param("providerName") String providerName);
}
