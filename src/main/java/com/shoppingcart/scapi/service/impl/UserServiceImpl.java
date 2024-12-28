package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.CreateUserRequest;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.dto.UpdateUserRequest;
import com.shoppingcart.scapi.entity.User;
import com.shoppingcart.scapi.exception.UserNotFoundException;
import com.shoppingcart.scapi.repo.UserRepo;
import com.shoppingcart.scapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        User user = null;
        try {
            user = userRepo.findById(userId).get();
            if (user == null) {
                ResponseCode.USER_NOT_FOUND.setReason("Invalid ID or User ID does not exist in the database.");
                throw new UserNotFoundException(ResponseCode.USER_NOT_FOUND);
            }
            return user;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(ResponseCode.USER_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.USER_NOT_FOUND.setReason(e.getMessage());
            throw new UserNotFoundException(ResponseCode.USER_NOT_FOUND);
        }
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return null;
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
