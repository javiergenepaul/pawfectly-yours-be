package com.childsplay.pawfectly.functions.auth.service.impl;

import com.childsplay.pawfectly.common.model.User;
import com.childsplay.pawfectly.common.model.UserDetailsImpl;
import com.childsplay.pawfectly.functions.loginprovider.service.LoginProviderService;
import com.childsplay.pawfectly.functions.auth.dto.UserAuthResponse;
import com.childsplay.pawfectly.functions.auth.dto.UserLoginProviderRequest;
import com.childsplay.pawfectly.functions.auth.dto.UserLoginRequest;
import com.childsplay.pawfectly.functions.auth.dto.UserRegisterRequest;
import com.childsplay.pawfectly.common.enums.RoleEnum;
import com.childsplay.pawfectly.functions.auth.service.UserAuthService;
import com.childsplay.pawfectly.common.util.JwtUtil;
import com.childsplay.pawfectly.functions.session.service.SessionService;
import com.childsplay.pawfectly.functions.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final SessionService sessionService;

    @Autowired
    private final LoginProviderService loginProviderService;

    /**
     * Logs in a user with the provided login request.
     *
     * @param userLoginRequest The login request containing the user's email and password.
     * @return The user authentication response containing the user model and JWT token.
     */
    @Override
    public UserAuthResponse login(UserLoginRequest userLoginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.email(),
                        userLoginRequest.password()
                )
        );

        User user = userRepository.findByEmail(userLoginRequest.email())
                .orElseThrow();

        UserDetailsImpl userDetails = UserRegisterRequest.buildUserDetails(user);
        String jwtToken = jwtUtil.generateToken(userDetails);
        sessionService.create(user, jwtToken);
        return UserAuthResponse.build(user, jwtToken);
    }

    @Override
    public boolean verifyEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public UserAuthResponse loginUsingProvider(UserLoginProviderRequest userLoginProviderRequest) {
        Optional<User> user = userRepository.findByEmail(userLoginProviderRequest.email());

        if (user.isPresent()) {
            // create login provider
            loginProviderService.createIfNotExistForUser(user.get(), userLoginProviderRequest);

            UserDetailsImpl userDetails = UserRegisterRequest.buildUserDetails(user.get());
            String jwtToken = jwtUtil.generateToken(userDetails);
            sessionService.create(user.get(), jwtToken);
            return UserAuthResponse.build(user.get(), jwtToken);
        } else {
            User newUser = User.builder()
                    .email(userLoginProviderRequest.email())
                    .displayName(userLoginProviderRequest.name())
                    .roleEnum(RoleEnum.USER)
                    .build();

            newUser = userRepository.save(newUser);

            loginProviderService.create(newUser, userLoginProviderRequest);
            UserDetailsImpl userDetails = UserRegisterRequest.buildUserDetails(newUser);
            String jwtToken = jwtUtil.generateToken(userDetails);
            sessionService.create(newUser, jwtToken);

            return UserAuthResponse.build(newUser, jwtToken);
        }
    }

    /**
     * Registers a new user with the provided registration request.
     *
     * @param registerRequest The registration request containing user details.
     * @return The user authentication response containing the user model and JWT token.
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserAuthResponse register(UserRegisterRequest registerRequest) {
        User user = UserRegisterRequest.build(registerRequest, passwordEncoder);
        user = userRepository.save(user);

        UserDetailsImpl userDetails = UserRegisterRequest.buildUserDetails(user);
        String jwtToken = jwtUtil.generateToken(userDetails);
        sessionService.create(user, jwtToken);

        return UserAuthResponse.build(user, jwtToken);
    }

    /**
     * Retrieves the user details of the currently authenticated user.
     *
     * @return The user details of the currently authenticated user, or null if no user is authenticated.
     */
    @Override
    public UserDetailsImpl getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (UserDetailsImpl) authentication.getPrincipal();
    }

}
