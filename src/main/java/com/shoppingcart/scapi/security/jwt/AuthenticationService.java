package com.shoppingcart.scapi.security.jwt;

import com.shoppingcart.scapi.dto.*;
import com.shoppingcart.scapi.exception.UserCreateFailedException;

public interface AuthenticationService {
    UserDto signupAdmin(SignUpAdminRequest request) throws UserCreateFailedException;
    JwtAuthenticationResponse signin(SignInRequest signinRequest);
    JwtAuthenticationResponse genarateNewTokenUsingRefreshToken(NewTokenRequest newTokenRequest) throws Exception;
    void logout(String refreshToken);
}
