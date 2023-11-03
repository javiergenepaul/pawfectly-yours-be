package com.childsplay.pawfectly.functions.LoginProvider.service.impl;

import com.childsplay.pawfectly.common.model.LoginProvider;
import com.childsplay.pawfectly.common.model.User;
import com.childsplay.pawfectly.functions.LoginProvider.repository.LoginProviderRepository;
import com.childsplay.pawfectly.functions.LoginProvider.service.LoginProviderService;
import com.childsplay.pawfectly.functions.auth.dto.UserLoginProviderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginProviderServiceImpl implements LoginProviderService {

    @Autowired
    private final LoginProviderRepository loginProviderRepository;

    /**
     * Creates a new LoginProvider entry in the database associated with a user and a third-party login provider.
     *
     * @param user     The user to associate with the new LoginProvider entry.
     * @param request  The UserLoginProviderRequest containing information about the third-party provider.
     */
    @Override
    public void create(User user, UserLoginProviderRequest request) {
        LoginProvider loginProvider = LoginProvider.builder()
                .user(user)
                .providerName(request.providerName())
                .providerId(request.providerId())
                .isDeleted(false)
                .isActive(true)
                .build();

        loginProviderRepository.save(loginProvider);
    }

    /**
     * Creates a new LoginProvider entry for a user with a third-party login provider if it does not already exist.
     *
     * @param user     The user for whom the LoginProvider entry should be created.
     * @param request  The UserLoginProviderRequest containing information about the third-party provider.
     */
    public void createIfNotExistForUser(User user, UserLoginProviderRequest request) {
        // Check if a LoginProvider entry already exists for the specified user and provider
        Optional<LoginProvider> loginProvider = loginProviderRepository
                .findByUserIdAndProviderName(user.getId(), request.providerName().toString());

        // If no entry is found, create a new LoginProvider entry
        if (loginProvider.isEmpty()) {
            create(user, request);
        }
    }
}
