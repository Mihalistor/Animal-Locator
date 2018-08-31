package com.animal.locator.animalLocator.controllers;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.*;
import com.animal.locator.animalLocator.utils.UserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;
import java.util.List;

@Controller
public class HomeController {

    private final ModelAndView mav = new ModelAndView();

    @Autowired
    AnimalService animalService;

    @Autowired
    TreatmentService treatmentService;

    @Autowired
    GpsDataService gpsDataService;

    @Autowired
    GpsLocatorService gpsLocatorService;

    @Autowired
    SafeZoneService safeZoneService;

    @Autowired
    UserService userService;

    @Autowired
    UserResolver userResolver;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView showHomePage()
    {
        List<Animal> animalsList = animalService.findByUser(userResolver.authUser());
        mav.getModelMap().addAttribute("animalsList", animalsList);
        mav.setViewName("/home");
        return mav;
    }

}