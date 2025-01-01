package com.shoppingcart.scapi.controllers;

import com.shoppingcart.scapi.dto.APIResponseDto;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.User;
import com.shoppingcart.scapi.exception.UserNotFoundException;
import com.shoppingcart.scapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<APIResponseDto> getUserById(@PathVariable Long userId) {
        User user = null;
        try {
            user = userService.getUserById(userId);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Get user successfully!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, user));
    }
}
