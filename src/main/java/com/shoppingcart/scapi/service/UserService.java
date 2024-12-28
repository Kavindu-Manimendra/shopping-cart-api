package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.dto.CreateUserRequest;
import com.shoppingcart.scapi.dto.UpdateUserRequest;
import com.shoppingcart.scapi.entity.User;
import com.shoppingcart.scapi.exception.UserNotFoundException;

public interface UserService {
    User getUserById(Long userId) throws UserNotFoundException;
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
}
