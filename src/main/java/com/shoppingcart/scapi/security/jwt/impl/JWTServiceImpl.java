package com.shoppingcart.scapi.security.jwt.impl;

import com.shoppingcart.scapi.entity.RefreshToken;
import com.shoppingcart.scapi.entity.User;
import com.shoppingcart.scapi.repo.RefreshTokenRepo;
import com.shoppingcart.scapi.security.jwt.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecretKey;

    @Value("${auth.token.expirationInMils}")
    private long expirationTime; //1000 * 60 * 60 * 24 = 24 hours

    @Value("${auth.refreshToken.expirationInSeconds}")
    private long refreshTokenExpirationTime;

    private final RefreshTokenRepo refreshTokenRepo;

    @Override
    @Transactional
    public String generateToken(UserDetails userDetails, Long userId) {
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject(userDetails.getUsername())
                .claim("id", userId)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    @Transactional
    public String generateRefreshToken(User user) {
        String refreshToken = UUID.randomUUID().toString();

        RefreshToken rToken = new RefreshToken();
        rToken.setUser(user);
        rToken.setRefreshToken(refreshToken);
        rToken.setCreatedAt(Instant.now());
        rToken.setExpiresAt(Instant.now().plusSeconds(refreshTokenExpirationTime));

        refreshTokenRepo.save(rToken);
        return refreshToken;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Key getSigninKey() {
        byte[] key = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String accessToken, UserDetails userDetails) {
        final String userEmail = extractUsername(accessToken);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(accessToken));
    }

    private boolean isTokenExpired(String accessToken) {
        return extractClaim(accessToken, Claims::getExpiration).before(new Date());
    }

    public boolean isRefreshTokenExpired(RefreshToken refreshToken) {
        if (refreshToken.getExpiresAt().compareTo(Instant.now()) <= 0) {
            return true;
        }
        return false;
    }
}
