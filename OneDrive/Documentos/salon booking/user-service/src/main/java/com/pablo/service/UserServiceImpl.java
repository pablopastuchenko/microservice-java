package com.pablo.service;

import com.pablo.exception.UserException;
import com.pablo.modal.User;
import com.pablo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserException {
        Optional<User> otp  = userRepository.findById(id);
        if (otp.isPresent()){
            return otp.get();
        }
        throw new UserException("user not found");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) throws UserException {
        Optional<User> otp = userRepository.findById(id);
        if (otp.isEmpty()){
            throw new UserException("user not found with id" + id);
        }
        userRepository.delete(otp.get());

    }

    @Override
    public User updateUser(Long id, User user) throws UserException {
        Optional<User> otp = userRepository.findById(id);
        if (otp.isEmpty()){
            throw new UserException("user not found with id" + id);
        }
        User existingUser= otp.get();

        existingUser.setFullname(user.getFullname());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setUsername(user.getUsername());

        return userRepository.save(existingUser);
    }
}
