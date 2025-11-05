package com.scoreshelf.scorestore.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scoreshelf.scorestore.base.entity.User;
import com.scoreshelf.scorestore.base.repository.UserRepository;
import com.scoreshelf.scorestore.base.service.IAuthenticaationService;

@Service
public class AuthServiceImpl implements IAuthenticaationService {

    @Autowired
    private UserRepository userRepository;

    public User validateUserAndGetUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            return user; 
        }
        return null; 
    }

    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username).orElse(null); 
    }

    public void updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        userRepository.save(user); 
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }

}