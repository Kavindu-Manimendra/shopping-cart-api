package com.shoppingcart.scapi.controllers;

import com.shoppingcart.scapi.dto.*;
import com.shoppingcart.scapi.exception.UserCreateFailedException;
import com.shoppingcart.scapi.security.jwt.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<APIResponseDto> login(@RequestBody SignInRequest request) {
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        try {
            jwtResponse = authenticationService.signin(request);
        } catch (AuthenticationException e) {
            ResponseCode.ERROR.setReason("Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(APIResponseDto.getInstance(ResponseCode.ERROR, null));
        } catch (Exception e) {
            ResponseCode.ERROR.setReason("Something went wrong! Please go to login page.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponseDto.getInstance(ResponseCode.ERROR, null));
        }
        ResponseCode.SUCCESS.setReason("Login Successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, jwtResponse));
    }

    @PostMapping("/signup-admin")
    public ResponseEntity<APIResponseDto> signupAdmin(@RequestBody SignUpAdminRequest request) {
        UserDto userDto = null;
        try {
            userDto = authenticationService.signupAdmin(request);
        } catch (UserCreateFailedException e) {
            ResponseCode.ERROR.setReason("Account creation failed!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(APIResponseDto.getInstance(ResponseCode.ERROR, null));
        } catch (Exception e) {
            ResponseCode.ERROR.setReason("Account creation failed!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponseDto.getInstance(ResponseCode.ERROR, null));
        }
        ResponseCode.SUCCESS.setReason("Create admin successfully!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, userDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<APIResponseDto> refresh(@RequestBody NewTokenRequest newTokenRequest) {
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        try {
            jwtResponse = authenticationService.genarateNewTokenUsingRefreshToken(newTokenRequest);
        } catch (Exception e) {
            ResponseCode.ERROR.setReason("Refresh token expired!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(APIResponseDto.getInstance(ResponseCode.ERROR, null));
        }
        ResponseCode.SUCCESS.setReason("New access token refreshed!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, jwtResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<APIResponseDto> logout(@RequestBody NewTokenRequest refreshToken) {
        try {
            authenticationService.logout(refreshToken.getRefreshToken());
        } catch (Exception e) {
            ResponseCode.ERROR.setReason("Something went wrong! Please go to login page.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponseDto.getInstance(ResponseCode.ERROR, null));
        }
        ResponseCode.SUCCESS.setReason("Logout successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, null));
    }
}
