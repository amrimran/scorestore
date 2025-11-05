package com.scoreshelf.scorestore.base.service;

import com.scoreshelf.scorestore.base.entity.User;
import java.util.List;

public interface IAuthenticaationService {
    User validateUserAndGetUser(String username, String password);
    User getCurrentUser(String username);
    void updateUser(User user);
    List<User> getAllUsers(); 
    void registerUser(User user);
}