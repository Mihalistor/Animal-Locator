package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.repository.UserRepository;
import com.animal.locator.animalLocator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserId(int id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, bCryptPasswordEncoder.encode(password));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsernameAndStatus(String username, int status) {
        return userRepository.findByUsernameAndStatus(username, status);
    }

    @Override
    public List<User> findByStatusOrderByUsernameAsc(int status) {
        return userRepository.findByStatusOrderByUsernameAsc(status);
    }
}
