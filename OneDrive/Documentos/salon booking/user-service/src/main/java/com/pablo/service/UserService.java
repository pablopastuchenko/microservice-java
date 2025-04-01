package com.pablo.service;

import com.pablo.exception.UserException;
import com.pablo.modal.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id) throws UserException;
    List<User> getAllUsers();
    void deleteUser(Long id) throws UserException;
    User updateUser(Long id, User user) throws UserException;
}
