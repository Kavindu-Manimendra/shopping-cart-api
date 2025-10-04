package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.CreateUserRequest;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.dto.UpdateUserRequest;
import com.shoppingcart.scapi.dto.UserDto;
import com.shoppingcart.scapi.entity.User;
import com.shoppingcart.scapi.enums.Role;
import com.shoppingcart.scapi.exception.UserCreateFailedException;
import com.shoppingcart.scapi.exception.UserDeleteFailedException;
import com.shoppingcart.scapi.exception.UserNotFoundException;
import com.shoppingcart.scapi.exception.UserUpdateFailedException;
import com.shoppingcart.scapi.repo.UserRepo;
import com.shoppingcart.scapi.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

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
    public UserDto createUser(CreateUserRequest request) throws UserCreateFailedException {
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
            user.setRole(Role.USER);

            User newUser = userRepo.save(user);
            return convertUserToDto(newUser);
        } catch (UserCreateFailedException e) {
            throw new UserCreateFailedException(ResponseCode.USER_CREATE_FAIL);
        } catch (Exception e) {
            ResponseCode.USER_CREATE_FAIL.setReason(e.getMessage());
            throw new UserCreateFailedException(ResponseCode.USER_CREATE_FAIL);
        }
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) throws UserUpdateFailedException {
        User existingUser = null;
        try {
            existingUser = getUserById(userId);
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepo.save(existingUser);
        } catch (UserNotFoundException e) {
            throw new UserUpdateFailedException(ResponseCode.USER_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.USER_UPDATE_FAIL.setReason(e.getMessage());
            throw new UserUpdateFailedException(ResponseCode.USER_UPDATE_FAIL);
        }
    }

    @Override
    public void deleteUser(Long userId) throws UserDeleteFailedException {
        User user = null;
        try {
            user = getUserById(userId);
            userRepo.delete(user);
        } catch (UserNotFoundException e) {
            throw new UserDeleteFailedException(ResponseCode.USER_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.USER_DELETE_FAIL.setReason(e.getMessage());
            throw new UserDeleteFailedException(ResponseCode.USER_DELETE_FAIL);
        }
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() throws UserNotFoundException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            return userRepo.findByEmail(email).get();
        } catch (Exception e) {
            throw new UserNotFoundException(ResponseCode.USER_NOT_FOUND);
        }
    }
}
