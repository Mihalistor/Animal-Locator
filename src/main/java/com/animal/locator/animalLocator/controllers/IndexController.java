package com.animal.locator.animalLocator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController
{
    private final ModelAndView mav = new ModelAndView();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showIndex()
    {
        mav.setViewName("redirect:/home");
        return mav;
    }

}