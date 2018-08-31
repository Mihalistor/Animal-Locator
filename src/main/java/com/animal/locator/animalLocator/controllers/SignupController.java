package com.animal.locator.animalLocator.controllers;

import com.animal.locator.animalLocator.forms.SignUpForm;
import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.UserService;
import com.animal.locator.animalLocator.validators.SignUpValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SignupController {

    @Autowired
    private SignUpValidator signUpValidator;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    private final ModelAndView mav = new ModelAndView();

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView showForm()
    {
        final SignUpForm signUpForm = new SignUpForm();
        mav.getModelMap().addAttribute("signUpForm", signUpForm);
        mav.setViewName("/signup");
        return mav;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView signup(@Valid final SignUpForm signUpForm, final BindingResult bindingResult, final HttpServletRequest request)
    {
        ValidationUtils.invokeValidator(signUpValidator, signUpForm, bindingResult);

        if (bindingResult.hasErrors())
        {
            mav.getModelMap().addAttribute("signUpForm", signUpForm);
            mav.setViewName("/signup");
        }
        else
        {
            final User user = new User();
            user.setUsername(signUpForm.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(signUpForm.getPassword()));
            user.setFirstname(signUpForm.getFirstname());
            user.setLastname(signUpForm.getLastname());
            user.setEmail(signUpForm.getEmail());
            user.setPhone(signUpForm.getPhone());
            userService.save(user);
            mav.getModelMap().addAttribute("signUpForm", signUpForm);
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

}
