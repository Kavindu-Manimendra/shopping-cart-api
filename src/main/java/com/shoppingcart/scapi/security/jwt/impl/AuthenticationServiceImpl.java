package com.shoppingcart.scapi.security.jwt.impl;

import com.shoppingcart.scapi.dto.*;
import com.shoppingcart.scapi.entity.RefreshToken;
import com.shoppingcart.scapi.entity.User;
import com.shoppingcart.scapi.enums.Role;
import com.shoppingcart.scapi.exception.UserCreateFailedException;
import com.shoppingcart.scapi.repo.RefreshTokenRepo;
import com.shoppingcart.scapi.repo.UserRepo;
import com.shoppingcart.scapi.security.jwt.AuthenticationService;
import com.shoppingcart.scapi.security.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final RefreshTokenRepo refreshTokenRepo;

    @Override
    @Transactional
    public UserDto signupAdmin(SignUpAdminRequest request) throws UserCreateFailedException {
        try {
            if (userRepo.existsByEmail(request.getEmail())) {
                ResponseCode.USER_CREATE_FAIL.setReason("Email already exists in the database.");
                throw new UserCreateFailedException(ResponseCode.USER_CREATE_FAIL);
            }
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.ADMIN);

            User newUser = userRepo.save(user);

            UserDto userDto = new UserDto();
            userDto.setId(newUser.getId());
            userDto.setEmail(newUser.getEmail());
            return userDto;
        } catch (UserCreateFailedException e) {
            throw new UserCreateFailedException(ResponseCode.USER_CREATE_FAIL);
        } catch (Exception e) {
            ResponseCode.USER_CREATE_FAIL.setReason(e.getMessage());
            throw new UserCreateFailedException(ResponseCode.USER_CREATE_FAIL);
        }
    }

    @Override
    @Transactional
    public JwtAuthenticationResponse signin(SignInRequest signinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signinRequest.getEmail(), signinRequest.getPassword()));

        User user = userRepo.findByEmail(signinRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password!"));

        var jwt = jwtService.generateToken(user, user.getId());
        var refreshToken = jwtService.generateRefreshToken(user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        jwtAuthenticationResponse.setUserDto(userDto);
        return jwtAuthenticationResponse;
    }

    @Override
    @Transactional
    public JwtAuthenticationResponse genarateNewTokenUsingRefreshToken(NewTokenRequest newTokenRequest) throws Exception {
        RefreshToken refreshToken = refreshTokenRepo.findByRefreshToken(newTokenRequest.getRefreshToken()).get();
        if (jwtService.isRefreshTokenExpired(refreshToken)) {
            refreshTokenRepo.delete(refreshToken);
            throw new Exception("Refresh token was expired. Please make a new signin request");
        }
        String newAccessToken = jwtService.generateToken(refreshToken.getUser(), refreshToken.getUser().getId());

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(newAccessToken);
        jwtAuthenticationResponse.setRefreshToken(newTokenRequest.getRefreshToken());

        User user = refreshToken.getUser();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        jwtAuthenticationResponse.setUserDto(userDto);

        return jwtAuthenticationResponse;
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenRepo.findByRefreshToken(refreshToken).get();
        refreshTokenRepo.delete(refreshTokenEntity);
    }
}