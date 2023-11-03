package com.childsplay.pawfectly.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private final String secretKey;

    @Value("${jwt.expiration}")
    private final long expiration;

    @Value("${jwt.refresh-expiration}")
    private final long refreshExpiration;


    /**
     * Generates a token for the given user details.
     *
     * @param userDetails The user details for which the token is generated.
     * @return The generated token.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }


    /**
     * Generates a JWT token with the provided extra claims and user details.
     *
     * @param extraClaims Additional claims to include in the token
     * @param userDetails The user details associated with the token
     * @return The generated JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Checks if a given token is valid for a specific user.
     *
     * @param token The token to be validated.
     * @param userDetails The user details of the user.
     * @return True if the token is valid for the user, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUserEmailAddress(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }

    /**
     * Checks if a given token has expired.
     *
     * @param token The token to check for expiration.
     * @return True if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return getUserExpiryDate(token).before(new Date());
    }

    /**
     * Retrieves all claims from a JWT token.
     *
     * @param token The JWT token
     * @return The claims extracted from the token
     */
    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves a specific claim from a JWT token.
     *
     * @param token The JWT token
     * @param claimsResolver A function that resolves the desired claim from the token's claims
     * @param <T> The type of the claim
     * @return The resolved claim
     */
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves the signing key used for authentication.
     *
     * @return The signing key as a Key object.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Retrieves the email address associated with the user token.
     *
     * @param token The user token
     * @return The email address associated with the token
     */
    public String getUserEmailAddress(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Retrieves the issued at (iat) claim from the user's token.
     *
     * @param token The user's token.
     * @return The issued at date.
     */
    public Date getUserIssuedAt(String token) {
        return getClaim(token, Claims::getIssuedAt);
    }

    /**
     * Retrieves the expiration date of a user's token.
     *
     * @param token The user's token.
     * @return The expiration date of the token.
     */
    public Date getUserExpiryDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Retrieves the expiration date and time of a user's token as a LocalDateTime object.
     *
     * @param token The user's token
     * @return The expiration date and time as a LocalDateTime object
     */
    public LocalDateTime getUserExpiryLocalDateTime(String token) {
        Date date = getClaim(token, Claims::getExpiration);
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime().withNano(0);
    }

    /**
     * Retrieves the LocalDateTime representation of the "issued at" claim from the provided token.
     *
     * @param token The token from which to retrieve the claim.
     * @return The LocalDateTime representation of the "issued at" claim.
     */
    public LocalDateTime getUserIssuedAtLocalDateTime(String token) {
        Date date = getClaim(token, Claims::getIssuedAt);
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime().withNano(0);
    }

}
