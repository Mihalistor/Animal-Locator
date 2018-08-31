package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.User;

import java.util.List;

public interface UserService {

    User save(User user);
    User updateUser(User user);
    List<User> findAll();
    User findByUserId(int id);
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    User findByEmail(String email);
    User findByUsernameAndStatus(String username, int status);
    List<User> findByStatusOrderByUsernameAsc(int status);

}
