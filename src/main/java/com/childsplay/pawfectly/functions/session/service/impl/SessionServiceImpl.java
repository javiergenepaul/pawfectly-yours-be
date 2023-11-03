package com.childsplay.pawfectly.functions.session.service.impl;

import com.childsplay.pawfectly.common.util.JwtUtil;
import com.childsplay.pawfectly.common.model.Session;
import com.childsplay.pawfectly.functions.session.repository.SessionRepository;
import com.childsplay.pawfectly.functions.session.service.SessionService;
import com.childsplay.pawfectly.common.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    @Autowired
    private final SessionRepository sessionRepository;

    @Autowired
    private final JwtUtil jwtUtil;

    /**
     * Creates a new session for the given user model with the provided token.
     * If a session already exists for the user, the token, expiry time, and issued at
     * values will be updated. Otherwise, a new session will be created.
     *
     * @param user The user model for which the session is being created
     * @param token The token to be associated with the session
     */
    @Override
    public void create(User user, String token) {
        Optional<Session> session = sessionRepository.findFirstByUserMasterId(user.getId());
        if (session.isPresent()) {
            session.get().setToken(token);
            session.get().setExpiryTime(jwtUtil.getUserExpiryLocalDateTime(token));
            session.get().setIssuedAt(jwtUtil.getUserIssuedAtLocalDateTime(token));
            sessionRepository.save(session.get());
        } else {
            Session newSession = Session.builder()
                    .token(token)
                    .userMaster(user)
                    .email(user.getEmail())
                    .expiryTime(jwtUtil.getUserExpiryLocalDateTime(token))
                    .issuedAt(jwtUtil.getUserIssuedAtLocalDateTime(token))
                    .build();
            sessionRepository.save(newSession);
        }
    }
}
