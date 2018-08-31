package com.animal.locator.animalLocator.controllers;

import com.animal.locator.animalLocator.forms.CreateAnimalForm;
import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.AnimalSex;
import com.animal.locator.animalLocator.models.GpsLocator;
import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.*;
import com.animal.locator.animalLocator.utils.TabNavigationResolver;
import com.animal.locator.animalLocator.validators.CreateAnimalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class AdminPanelController {

    @Autowired
    UserService userService;

    @Autowired
    AnimalService animalService;

    @Autowired
    GpsLocatorService gpsLocatorService;

    @Autowired
    ColorService colorService;

    @Autowired
    BreedService breedService;

    @Autowired
    SpeciesService speciesService;

    @Autowired
    CreateAnimalValidator createAnimalValidator;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelAndView mav = new ModelAndView();

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView showAdminPanel(final HttpServletRequest request)
    {
        final int active = TabNavigationResolver.resolveActive(request.getParameter("tab"));
        mav.getModelMap().addAttribute("tab", active);

        List<User> activeUsers = new ArrayList<>();
        List<User> notActiveUsers = new ArrayList<>();
        List<User> onHoldUsers = new ArrayList<>();

        for (User user : userService.findAll()) {
            switch(user.getStatus()) {
                case 0:
                    onHoldUsers.add(user);
                    break;
                case 1:
                    activeUsers.add(user);
                    break;
                case 2:
                    notActiveUsers.add(user);
                default:
                    break;
            }
        }
        mav.getModelMap().addAttribute("activeUsers", activeUsers);
        mav.getModelMap().addAttribute("onHoldUsers", onHoldUsers);
        mav.getModelMap().addAttribute("notActiveUsers", notActiveUsers);
        CreateAnimalForm createAnimalForm = new CreateAnimalForm();
        createAnimalForm.setUserList(userService.findByStatusOrderByUsernameAsc(1));
        mav.getModelMap().addAttribute("createAnimalForm", createAnimalForm);
        mav.setViewName("/admin_panel");
        return mav;
    }

    @RequestMapping(value = "/admin/delete-user/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable("id") final int id, final HttpServletRequest request)
    {
        User user = userService.findByUserId(id);
        user.setStatus(2);
        userService.updateUser(user);
        mav.getModelMap().addAttribute("tab", "not_active_users");
        mav.setViewName("redirect:/admin");
        return mav;
    }

    @RequestMapping(value = {"/admin/activate-user/{id}", "/admin/restore-user/{id}"}, method = RequestMethod.GET)
    public ModelAndView activateUser(@PathVariable("id") final int id, final HttpServletRequest request)
    {
        User user = userService.findByUserId(id);
        user.setStatus(1);
        userService.updateUser(user);
        mav.getModelMap().addAttribute("tab", "active_users");
        mav.setViewName("redirect:/admin");
        return mav;
    }

    @RequestMapping(value = "/admin/create-animal", method = RequestMethod.POST)
    public ModelAndView createAnimal(@Valid final CreateAnimalForm createAnimalForm, final BindingResult bindingResult, final HttpServletRequest request)
    {
        ValidationUtils.invokeValidator(createAnimalValidator, createAnimalForm, bindingResult);
        if (bindingResult.hasErrors()) {
            createAnimalForm.setUserList(userService.findByStatusOrderByUsernameAsc(1));
            mav.getModelMap().addAttribute("createAnimalForm", createAnimalForm);
            mav.getModelMap().addAttribute("tab", 4);
            mav.setViewName("/admin_panel");
        } else {
            String[] devices = createAnimalForm.getDevices().replace(" ","").split(";");
            String[] tokens = createAnimalForm.getTokens().replace(" ","").split(";");
            for (int i = 0; i < createAnimalForm.getNumberOfAnimals(); i++) {
                GpsLocator gpsLocator = new GpsLocator();
                gpsLocator.setStatus(false);
                gpsLocator.setLed(false);
                gpsLocator.setDeviceId(Base64.getEncoder().encodeToString(devices[i].getBytes()));
                gpsLocator.setAccessToken(Base64.getEncoder().encodeToString(tokens[i].getBytes()));
                gpsLocator.setCreatedTime(LocalDateTime.now());
                gpsLocatorService.save(gpsLocator);
                Animal animal = new Animal();
                animal.setAnimalName(createAnimalForm.getUser().getUsername() + "'s animal");
                animal.setAnimalBirthday(LocalDateTime.now());
                animal.setLastNotification(LocalDateTime.now());
                animal.setAnimalSex(AnimalSex.MALE);
                animal.setUser(createAnimalForm.getUser());
                animal.setColor(colorService.findByColorId(100));
                animal.setBreed(breedService.findByBreedId(100));
                animal.setSpecies(speciesService.findBySpeciesId(100));
                animal.setAnimalTransponderLocation("-");
                animal.setAnimalTransponderCode("-");
                animal.setAnimalPassportCode("-");
                animal.setGpsLocator(gpsLocator);
                animalService.save(animal);
            }
            mav.getModelMap().addAttribute("tab", "create_animal");
            mav.setViewName("redirect:/admin");
        }
        return mav;
    }

}