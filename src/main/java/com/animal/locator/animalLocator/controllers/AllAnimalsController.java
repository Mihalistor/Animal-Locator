package com.animal.locator.animalLocator.controllers;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.GpsLocator;
import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.AnimalService;
import com.animal.locator.animalLocator.services.GpsLocatorService;
import com.animal.locator.animalLocator.services.ParticleAPIService;
import com.animal.locator.animalLocator.services.UserService;
import com.animal.locator.animalLocator.utils.UserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AllAnimalsController {

    private final ModelAndView mav = new ModelAndView();

    @Autowired
    AnimalService animalService;

    @Autowired
    GpsLocatorService gpsLocatorService;

    @Autowired
    UserService userService;

    @Autowired
    ParticleAPIService particleAPIService;

    @Autowired
    UserResolver userResolver;

    @RequestMapping(value = "/all-animals", method = RequestMethod.GET)
    public ModelAndView showAllAnimals()
    {
        List<Animal> allAnimalsList = animalService.findByUser(userResolver.authUser());
        mav.getModelMap().addAttribute("allAnimalsList", allAnimalsList);
        mav.setViewName("/all_animals");
        return mav;
    }

    @RequestMapping(value = {"/all-animals/{id}/{status}", "/home/{id}/{status}", "/animal/{id}/{status}"}, method = RequestMethod.GET)
    public ModelAndView animalStatus(@PathVariable("id") final int id, @PathVariable("status") final Boolean status, final HttpServletRequest request)
    {
        GpsLocator gpsLocator = gpsLocatorService.findByGpsLocatorId(id);
        if(userResolver.authUser().equals(animalService.findByGpsLocator(gpsLocator).getUser())) {
            gpsLocator.setStatus(!status);
            if (!gpsLocator.getStatus()) {
                gpsLocator.setLed(false);
                particleAPIService.sendMessageToPhoton(gpsLocator, gpsLocator.getLed().toString().toUpperCase(), "light");
            }
            particleAPIService.sendMessageToPhoton(gpsLocator, gpsLocator.getStatus().toString().toUpperCase(), "status");
            gpsLocatorService.update(gpsLocator);
            if (request.getServletPath().contains("/all-animals/")) {
                mav.setViewName("redirect:/all-animals");
            } else if (request.getServletPath().contains("/home/")) {
                mav.setViewName("redirect:/home");
            } else {
                mav.setViewName("redirect:/animal/{id}");
            }
        } else {
            mav.setViewName("/error");
        }
        return mav;
    }

}