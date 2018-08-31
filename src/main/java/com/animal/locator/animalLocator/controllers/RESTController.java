package com.animal.locator.animalLocator.controllers;

import com.animal.locator.animalLocator.models.*;
import com.animal.locator.animalLocator.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RESTController {

    @Autowired
    GpsDataService gpsDataService;

    @Autowired
    AnimalService animalService;

    @Autowired
    SafeZoneService safeZoneService;

    @Autowired
    UserService userService;

    @Autowired
    AnimalStatisticService animalStatisticService;

    @RequestMapping(value = "/rest/AnimalsLastLocation", method = RequestMethod.GET)
    public List<GpsData> animalsLastLocation() {
        List<GpsData> locations = new ArrayList<>();

        List<Animal> allAnimals = animalService.findByUser(authUser());
        for (Animal animal : allAnimals) {
            if (animal.getGpsLocator().getStatus()) {
                locations.add(gpsDataService.findFirstByAnimalOrderByGpsDataIdDesc(animal));
            }
        }
        if (locations.isEmpty() && allAnimals.size() > 0) {
            locations.add(gpsDataService.findFirstByAnimalOrderByGpsDataIdDesc(allAnimals.get(0)));
        }
        return locations;
    }

    @RequestMapping(value = "/rest/AnimalsLastLocation/{id}", method = RequestMethod.GET)
    public GpsData animalsLastLocation(@PathVariable("id") final int id) {
        Animal animal = animalService.findByAnimalId(id);
        return gpsDataService.findFirstByAnimalOrderByGpsDataIdDesc(animal);
    }

    @RequestMapping(value = "/rest/animal/{id}", method = RequestMethod.GET)
    public List<GpsData> animalLocations(@PathVariable("id") final int id) {
        Animal animal = animalService.findByAnimalId(id);
        List<GpsData> locations = gpsDataService.findFirst10ByAnimalOrderByGpsDataIdDesc(animal);
        return locations;
    }

    @RequestMapping(value = "/rest/animal/{id}/statistic", method = RequestMethod.GET)
    public List<AnimalStatistic> animalStatistic(@PathVariable("id") final int id) {
        List<AnimalStatistic> statistic = animalStatisticService.findByAnimalAndCreatedTimeAfterOrderByCreatedTimeAsc(animalService.findByAnimalId(id), LocalDateTime.now().minusDays(5));
        return statistic;
    }

    @RequestMapping(value = "/rest/animalSafeZone/{id}", method = RequestMethod.GET)
    public List<SafeZone> animalSafeZone(@PathVariable("id") final int id) {
        Animal animal = animalService.findByAnimalId(id);
        List<SafeZone> safeZoneElements = safeZoneService.findByAnimal(animal);
        return safeZoneElements;
    }

    public User authUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName());
    }

}
