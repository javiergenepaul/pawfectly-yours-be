package com.childsplay.pawfectly.config;

import com.childsplay.pawfectly.common.util.JwtUtil;
import com.childsplay.pawfectly.functions.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private final SessionRepository sessionRepository;

    /**
     * This method performs the authentication and authorization filtering for incoming HTTP requests. It checks for a JWT
     * (JSON Web Token) in the "Authorization" header, validates it, and sets up the user's authentication if the token is
     * valid. It then continues the request processing by invoking the next filter in the chain.
     *
     * @param request       The HTTP request being processed.
     * @param response      The HTTP response associated with the request.
     * @param filterChain   The chain of filters for request processing.
     * @throws ServletException if a servlet exception occurs during filtering.
     * @throws IOException if an I/O exception occurs during filtering.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String emailAddress;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        emailAddress = jwtUtil.getUserEmailAddress(jwt);
        if (emailAddress != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(emailAddress);

            sessionRepository.findFirstByEmailAndToken(userDetails.getUsername(), jwt)
                    .orElseThrow(() -> new RuntimeException("unauthorized"));

            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

    }

}
