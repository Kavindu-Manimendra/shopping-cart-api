package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.dto.CreateUserRequest;
import com.shoppingcart.scapi.dto.UpdateUserRequest;
import com.shoppingcart.scapi.dto.UserDto;
import com.shoppingcart.scapi.entity.User;
import com.shoppingcart.scapi.exception.UserCreateFailedException;
import com.shoppingcart.scapi.exception.UserDeleteFailedException;
import com.shoppingcart.scapi.exception.UserNotFoundException;
import com.shoppingcart.scapi.exception.UserUpdateFailedException;

public interface UserService {
    User getUserById(Long userId) throws UserNotFoundException;
    User createUser(CreateUserRequest request)  throws UserCreateFailedException;
    User updateUser(UpdateUserRequest request, Long userId)  throws UserUpdateFailedException;
    void deleteUser(Long userId) throws UserDeleteFailedException;
    UserDto convertUserToDto(User user);
}
