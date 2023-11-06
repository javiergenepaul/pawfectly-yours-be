package com.childsplay.pawfectly.config;

import com.childsplay.pawfectly.common.model.User;
import com.childsplay.pawfectly.functions.auth.dto.UserRegisterRequest;
import com.childsplay.pawfectly.functions.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Autowired
    private final UserRepository userRepository;

    /**
     * This method creates and configures a UserDetailsService bean for authentication and authorization purposes.
     *
     * @return An implementation of the UserDetailsService interface that retrieves user information based on their email.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        try {
            return username -> {
                User user = userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Credential Error"));
                return UserRegisterRequest.buildUserDetails(user);
            };
        } catch (Exception exception) {
            throw new UsernameNotFoundException("Credential Error");
        }
    }

    /**
     * This method creates and configures an AuthenticationProvider bean for user authentication.
     *
     * @return An AuthenticationProvider that uses a DaoAuthenticationProvider with a custom UserDetailsService and password encoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Set the custom UserDetailsService for authentication
        authProvider.setUserDetailsService(userDetailsService());


        // Set the password encoder for securely comparing passwords
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * This method creates an AuthenticationManager bean that retrieves the AuthenticationManager from the provided AuthenticationConfiguration.
     *
     * @param config The AuthenticationConfiguration that holds the AuthenticationManager.
     * @return The AuthenticationManager configured for user authentication and authorization.
     * @throws Exception if an error occurs while retrieving the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * This method creates a PasswordEncoder bean configured to use BCrypt for secure password hashing.
     *
     * @return A PasswordEncoder instance that uses the BCrypt algorithm for secure password storage and retrieval.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
