package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.dto.CreateUserRequest;
import com.shoppingcart.scapi.dto.UpdateUserRequest;
import com.shoppingcart.scapi.entity.User;

public interface UserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
}
