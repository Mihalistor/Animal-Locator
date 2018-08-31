package com.animal.locator.animalLocator.validators;

import com.animal.locator.animalLocator.forms.UserForm;
import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component("userEditValidator")
public class UserProfileValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public void validate(Object o, Errors errors)
    {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User user = userService.findByUsername(auth.getName());

        final UserForm userForm = (UserForm) o;
        if(!ObjectUtils.isEmpty(userService.findByUsername(userForm.getUsername())) && !StringUtils.equals(userForm.getUsername(), user.getUsername()))
        {
            errors.rejectValue("username","username.exists", "This username is already taken!");
        }

        if(!ObjectUtils.isEmpty(userService.findByEmail(userForm.getEmail())) && !StringUtils.equals(userForm.getEmail(), user.getEmail()))
        {
            errors.rejectValue("email","email.exists", "This email is already taken!");
        }

        if(userForm.getChangePassword()) {
            if(!StringUtils.equals(userForm.getPassword(), userForm.getCpassword()))
            {
                errors.rejectValue("password", "password.notmatch", "Password and confirm password do not match!");
            }

            if(userForm.getPassword().length() < 5)
            {
                errors.rejectValue("password", "password.length", "Password must have minimum 5 letters!");
            }
        }
    }

    @Override
    public boolean supports(Class<?> aClass)
    {
        return UserForm.class.equals(aClass);
    }
}
