package com.childsplay.pawfectly.functions.session.service;

import com.childsplay.pawfectly.common.model.User;
import org.springframework.stereotype.Service;

@Service
public interface SessionService {

    /**
     * Creates a new user with the given user model and token.
     *
     * @param user The user model containing the user's information.
     * @param token The token associated with the user.
     */
    void create(User user, String token);

}
