package com.animal.locator.animalLocator.repository;

import com.animal.locator.animalLocator.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserId(int id);
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    User findByEmail(String email);
    User findByUsernameAndStatus(String username, int status);
    List<User> findByStatusOrderByUsernameAsc(int status);

}