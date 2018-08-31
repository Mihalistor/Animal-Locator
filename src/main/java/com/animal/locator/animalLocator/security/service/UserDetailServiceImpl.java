package com.animal.locator.animalLocator.security.service;

import java.util.HashSet;
import java.util.Set;

import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        final User user = userService.findByUsernameAndStatus(username, 1);

        if (ObjectUtils.isEmpty(user)) {
            return new org.springframework.security.core.userdetails.User(" ", " ", new HashSet<>());
        }

        final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(StringUtils.equals(username, "admin")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
