package com.animal.locator.animalLocator.controllers;

import com.animal.locator.animalLocator.forms.UserForm;
import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.UserService;
import com.animal.locator.animalLocator.validators.UserProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserProfileController {

    @Autowired
    UserService userService;

    @Autowired
    UserProfileValidator userProfileValidator;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelAndView mav = new ModelAndView();

    @RequestMapping(value = "/user-profile", method = RequestMethod.GET)
    public ModelAndView showUserProfile()
    {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User user = userService.findByUsername(auth.getName());
        UserForm userForm = new UserForm();
        userForm.setFirstname(user.getFirstname());
        userForm.setLastname(user.getLastname());
        userForm.setUsername(user.getUsername());
        userForm.setEmail(user.getEmail());
        userForm.setPhone(user.getPhone());
        userForm.setDelay(user.getDelay());
        userForm.setEmailNotification(user.getEmailNotification());
        userForm.setSMSNotification(user.getSMSNotification());
        userForm.setMobileNotification(user.getMobileNotification());
        userForm.setDeleteData(user.getDeleteData());
        mav.getModelMap().addAttribute("userForm", userForm);

        final Boolean successMessage = (Boolean) mav.getModelMap().get("successMessage");
        if (!ObjectUtils.isEmpty(successMessage))
        {
            if (!successMessage)
            {
                mav.getModelMap().addAttribute("success", false);
            }
            mav.getModelMap().addAttribute("successMessage", false);
        }
        mav.setViewName("/user_profile");
        return mav;
    }

    @RequestMapping(value = "/user_profile_edit", method = RequestMethod.POST)
    public ModelAndView editUserProfile(@Valid final UserForm userForm, final BindingResult bindingResult, final HttpServletRequest request)
    {
        ValidationUtils.invokeValidator(userProfileValidator, userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            userForm.setChangePassword(false);
            mav.getModelMap().addAttribute("userForm", userForm);
            mav.getModelMap().addAttribute("success", false);
            mav.setViewName("/user_profile");
        } else {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            final User user = userService.findByUsername(auth.getName());
            user.setFirstname(userForm.getFirstname());
            user.setLastname(userForm.getLastname());
            user.setUsername(userForm.getUsername());
            user.setEmail(userForm.getEmail());
            user.setPhone(userForm.getPhone());
            user.setDelay(userForm.getDelay());
            user.setEmailNotification(userForm.getEmailNotification());
            user.setSMSNotification(userForm.getSMSNotification());
            user.setMobileNotification(userForm.getMobileNotification());
            user.setDeleteData(userForm.getDeleteData());
            if(userForm.getChangePassword()) {
                user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
            }
            userService.updateUser(user);
            mav.getModelMap().addAttribute("success", true);
            mav.getModelMap().addAttribute("successMessage", true);
            mav.getModelMap().addAttribute("userForm", userForm);
            mav.setViewName("redirect:/user-profile");
        }
        return mav;
    }

}
