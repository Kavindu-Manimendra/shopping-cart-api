package com.shoppingcart.scapi.controllers;

import com.shoppingcart.scapi.dto.*;
import com.shoppingcart.scapi.entity.User;
import com.shoppingcart.scapi.exception.UserCreateFailedException;
import com.shoppingcart.scapi.exception.UserDeleteFailedException;
import com.shoppingcart.scapi.exception.UserNotFoundException;
import com.shoppingcart.scapi.exception.UserUpdateFailedException;
import com.shoppingcart.scapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<APIResponseDto> getUserById(@PathVariable Long userId) {
        UserDto userDto = null;
        try {
            User user = userService.getUserById(userId);
            userDto = userService.convertUserToDto(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Get user successfully!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, userDto));
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponseDto> createUser(@RequestBody CreateUserRequest request) {
        UserDto userDto = null;
        try {
            User user = userService.createUser(request);
            userDto = userService.convertUserToDto(user);
        } catch (UserCreateFailedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Create user successfully!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, userDto));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<APIResponseDto> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
        UserDto userDto = null;
        try {
            User user = userService.updateUser(request, userId);
            userDto = userService.convertUserToDto(user);
        } catch (UserUpdateFailedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Update user successfully!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, userDto));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<APIResponseDto> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
        } catch (UserDeleteFailedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Delete user successfully!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS));
    }
}