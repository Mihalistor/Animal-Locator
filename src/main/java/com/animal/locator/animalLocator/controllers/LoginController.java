package com.animal.locator.animalLocator.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private final ModelAndView mav = new ModelAndView();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) final String error, @RequestParam(value = "logout", required = false) final String logout, @RequestParam(value = "message", required = false) final String message, final Model model)
    {

        if (error != null)
        {
            model.addAttribute("error", true);
        }

        if (logout != null)
        {

            model.addAttribute("logout", true);
        }

        if (!StringUtils.isEmpty(message))
        {

            model.addAttribute("message", message);
        }

        mav.setViewName("/login");
        return mav;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(final HttpServletRequest request, final HttpServletResponse response, final Model model)
    {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!ObjectUtils.isEmpty(auth))
        {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        mav.setViewName("redirect:/login?logout");
        return mav;
    }

}
