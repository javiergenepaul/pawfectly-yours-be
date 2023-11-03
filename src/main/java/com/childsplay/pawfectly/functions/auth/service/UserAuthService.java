package com.childsplay.pawfectly.functions.auth.service;

import com.childsplay.pawfectly.functions.auth.dto.UserLoginProviderRequest;
import com.childsplay.pawfectly.functions.auth.dto.UserLoginRequest;
import com.childsplay.pawfectly.functions.auth.dto.UserAuthResponse;
import com.childsplay.pawfectly.functions.auth.dto.UserRegisterRequest;
import com.childsplay.pawfectly.common.model.UserDetailsImpl;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthService {

    /**
     * Logs in a user with the provided login request.
     *
     * @param userLoginRequest The login request containing user credentials.
     * @return The authentication response for the logged in user.
     */
    UserAuthResponse login(UserLoginRequest userLoginRequest);

    boolean verifyEmail(String email);

    UserAuthResponse loginUsingProvider(UserLoginProviderRequest userLoginProviderRequest);

    /**
     * Registers a new user with the provided registration request.
     *
     * @param registerRequest The registration request containing user information.
     * @return The response containing the authentication details of the registered user.
     */
    UserAuthResponse register(UserRegisterRequest registerRequest);

    /**
     * Retrieves the user details for the currently authenticated user.
     *
     * @return The user details for the authenticated user.
     */
    UserDetailsImpl getUserDetails();

}
