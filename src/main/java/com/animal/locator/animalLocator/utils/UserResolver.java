package com.animal.locator.animalLocator.utils;

import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserResolver {

    @Autowired
    private UserService userService;

    public User authUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName());
    }
}
