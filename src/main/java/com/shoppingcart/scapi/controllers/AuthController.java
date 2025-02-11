package com.shoppingcart.scapi.controllers;

import com.shoppingcart.scapi.dto.APIResponseDto;
import com.shoppingcart.scapi.dto.JwtResponse;
import com.shoppingcart.scapi.dto.LoginRequest;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.security.jwt.JwtUtils;
import com.shoppingcart.scapi.security.user.ShopUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // @Valid - use it not to get empty values for @NotBlank fields
    @PostMapping("/login")
    public ResponseEntity<APIResponseDto> login(@Valid @RequestBody LoginRequest request) {
        JwtResponse jwtResponse = null;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateTokenForUser(authentication);
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
            jwtResponse = new JwtResponse(userDetails.getId(), jwt);
        } catch (AuthenticationException e) {
            ResponseCode.ERROR.setReason("Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(APIResponseDto.getInstance(ResponseCode.ERROR, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Login Successful!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, jwtResponse));
    }

    // Todo : correct return response when user is unauthorized in CartController APIs
    // Todo : correct return response in catch block in every APIs
}
