package com.childsplay.pawfectly.functions.auth.dto;

import com.childsplay.pawfectly.common.enums.RoleEnum;
import com.childsplay.pawfectly.common.model.UserDetailsImpl;
import com.childsplay.pawfectly.common.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public record UserRegisterRequest(String email,
                                  String password,
                                  String firstName,
                                  String lastName,
                                  String displayName) {

    /**
     * Builds a UserModel object based on the provided UserRegisterRequest and PasswordEncoder.
     *
     * @param registerRequest The UserRegisterRequest containing the user's registration information.
     * @param passwordEncoder The PasswordEncoder used to hash the user's password.
     * @return The built UserModel object.
     */
    public static User build(UserRegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        String hashedPassword = passwordEncoder.encode(registerRequest.password());
        return User.builder()
                .email(registerRequest.email())
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .isActive(true)
                .displayName(registerRequest.displayName())
                .password(hashedPassword)
                .role(RoleEnum.USER)
                .build();
    }

    /**
     * Builds a UserDetailsImpl object using the provided UserModel.
     *
     * @param user The UserModel object containing user details.
     * @return A UserDetailsImpl object with the user details.
     */
    public static UserDetailsImpl buildUserDetails(User user) {
        return UserDetailsImpl.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roleEnum(user.getRole())
                .displayName(user.getDisplayName())
                .build();
    }
}
