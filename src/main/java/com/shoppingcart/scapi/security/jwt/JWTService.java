package com.shoppingcart.scapi.security.jwt;

import com.shoppingcart.scapi.entity.RefreshToken;
import com.shoppingcart.scapi.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String generateToken(UserDetails userDetails, Long userId);
    String extractUsername(String token);
    boolean isTokenValid(String refreshToken, UserDetails userDetails);
    String generateRefreshToken(User user);
    boolean isRefreshTokenExpired(RefreshToken refreshToken);
}
