package com.childsplay.pawfectly.functions.auth.dto;

import com.childsplay.pawfectly.common.model.User;

public record UserAuthResponse(Long id,
                               String email,
                               String firstName,
                               String LastName,
                               String displayName,
                               String token
) {

    /**
     * Builds a UserAuthResponse object using the provided UserModel and token.
     *
     * @param user The UserModel object containing user information.
     * @param token The authentication token for the user.
     * @return A UserAuthResponse object with the user's information and token.
     */
    public static UserAuthResponse build(User user, String token) {
        return new UserAuthResponse(user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getDisplayName(),
                token
        );
    }

}
