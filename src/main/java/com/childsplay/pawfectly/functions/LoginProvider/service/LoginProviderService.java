package com.childsplay.pawfectly.functions.LoginProvider.service;

import com.childsplay.pawfectly.common.model.User;
import com.childsplay.pawfectly.functions.auth.dto.UserLoginProviderRequest;
import org.springframework.stereotype.Service;

@Service
public interface LoginProviderService {

    /**
     * Creates a new LoginProvider entry in the database associated with a user and a third-party login provider.
     *
     * @param user     The user to associate with the new LoginProvider entry.
     * @param request  The UserLoginProviderRequest containing information about the third-party provider.
     */
    void create(User user, UserLoginProviderRequest request);

    /**
     * Creates a new LoginProvider entry for a user with a third-party login provider if it does not already exist.
     *
     * @param user     The user for whom the LoginProvider entry should be created.
     * @param request  The UserLoginProviderRequest containing information about the third-party provider.
     */
    void createIfNotExistForUser(User user, UserLoginProviderRequest request);

}
