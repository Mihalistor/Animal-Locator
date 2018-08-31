package com.animal.locator.animalLocator.validators;

import com.animal.locator.animalLocator.forms.SignUpForm;
import com.animal.locator.animalLocator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component("signUpValidator")
public class SignUpValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public void validate(Object o, Errors errors)
    {
        final SignUpForm signUpForm = (SignUpForm) o;
        if(!ObjectUtils.isEmpty(userService.findByUsername(signUpForm.getUsername())))
        {
            errors.rejectValue("username","username.exists", "This username is already taken!");
        }

        if(!StringUtils.equals(signUpForm.getPassword(), signUpForm.getConfirmPassword()))
        {
            errors.rejectValue("password", "password.notmatch", "Password and confirm password do not match!");
        }
        if(!ObjectUtils.isEmpty(userService.findByEmail(signUpForm.getEmail())))
        {
            errors.rejectValue("email","email.exists", "This email is already taken!");
        }
    }

    @Override
    public boolean supports(Class<?> aClass)
    {
        return SignUpForm.class.equals(aClass);
    }

}