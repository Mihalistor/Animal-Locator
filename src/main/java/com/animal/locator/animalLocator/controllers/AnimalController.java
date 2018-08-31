package com.animal.locator.animalLocator.controllers;

import com.animal.locator.animalLocator.forms.EditAnimalForm;
import com.animal.locator.animalLocator.forms.SafeZoneMapForm;
import com.animal.locator.animalLocator.forms.TreatmentForm;
import com.animal.locator.animalLocator.models.*;
import com.animal.locator.animalLocator.services.*;
import com.animal.locator.animalLocator.utils.TabNavigationResolver;
import com.animal.locator.animalLocator.utils.UserResolver;
import com.animal.locator.animalLocator.validators.TreatmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class AnimalController {

    private final ModelAndView mav = new ModelAndView();

    @Autowired
    AnimalService animalService;

    @Autowired
    TreatmentService treatmentService;

    @Autowired
    SafeZoneService safeZoneService;

    @Autowired
    AnimalStatisticService animalStatisticService;

    @Autowired
    GpsLocatorService gpsLocatorService;

    @Autowired
    ParticleAPIService particleAPIService;

    @Autowired
    PDFCreatorService pdfCreatorService;

    @Autowired
    ColorService colorService;

    @Autowired
    BreedService breedService;

    @Autowired
    SpeciesService speciesService;

    @Autowired
    TreatmentValidator treatmentValidator;

    @Autowired
    UserResolver userResolver;

    @RequestMapping(value = "/animal/{id}", method = RequestMethod.GET)
    public ModelAndView showAnimal(@PathVariable("id") final int id, final Model model, final HttpServletRequest request)
    {
        final int active = TabNavigationResolver.resolveActive(request.getParameter("tab"));
        mav.getModelMap().addAttribute("tab", active);
        Animal animal = animalService.findByAnimalId(id);
        if(userResolver.authUser().equals(animal.getUser())) {
            mav.getModelMap().addAttribute("animal", animal);
            AnimalStatistic statistic = animalStatisticService.findByAnimalAndCreatedTime(animal, LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
            if (ObjectUtils.isEmpty(statistic)) {
                statistic = new AnimalStatistic();
                statistic.setRunning(0);
                statistic.setSleeping(0);
            }
            mav.getModelMap().addAttribute("statistic", statistic);
            List<Treatment> treatmentsList = treatmentService.findByAnimalOrderByTreatmentDateDesc(animal);
            mav.getModelMap().addAttribute("treatmentsList", treatmentsList);
            TreatmentForm treatmentForm = new TreatmentForm();
            mav.getModelMap().addAttribute("newTreatmentForm", treatmentForm);
            SafeZoneMapForm safeZoneMapForm = new SafeZoneMapForm();
            List<Animal> animalsWithZone = new ArrayList<>();
            List<SafeZone> safeZones = safeZoneService.findAll();
            for (SafeZone safeZone : safeZones) {
                if (!animalsWithZone.contains(safeZone.getAnimal()) && safeZone.getAnimal().getAnimalId() != id) {
                    animalsWithZone.add(safeZone.getAnimal());
                }
            }
            safeZoneMapForm.setAnimalsZone(animalsWithZone);
            mav.getModelMap().addAttribute("safeZoneMapForm", safeZoneMapForm);
            mav.setViewName("/animal");
        }
        else {
            mav.setViewName("/error");
        }
        return mav;
    }

    @RequestMapping(value = "/animal/{id}/new_treatment", method = RequestMethod.POST)
    public ModelAndView newTreatment(@PathVariable("id") final int id, final TreatmentForm newTreatmentForm, final BindingResult bindingResult, final HttpServletRequest request)
    {
        ValidationUtils.invokeValidator(treatmentValidator, newTreatmentForm, bindingResult);
        if (bindingResult.hasErrors()) {
            mav.getModelMap().addAttribute("newTreatmentForm", newTreatmentForm);
            mav.getModelMap().addAttribute("tab", "treatments");
            mav.setViewName("redirect:/animal/{id}");
        } else {
            Animal animal = animalService.findByAnimalId(id);
            Treatment treatment = new Treatment();
            treatment.setTreatmentType(newTreatmentForm.getTreatmentType());
            treatment.setTreatmentDate(treatment.getTreatmentDateFromString(newTreatmentForm.getDateTime().replace("/", ".") + " 00:00"));
            treatment.setTreatmentDescription(newTreatmentForm.getTreatmentDescription());
            if(!newTreatmentForm.getAlertTime().isEmpty()) {
                treatment.setTreatmentAlertTime(treatment.getTreatmentAlertTimeFromString(newTreatmentForm.getAlertTime().replace("/", ".")));
                treatment.setTreatmentNotification(newTreatmentForm.getNotification());
            }
            if(!treatment.getTreatmentNotification()) {
                treatment.setTreatmentAlertTime(null);
            }
            treatment.setAnimal(animal);
            treatmentService.save(treatment);
            mav.getModelMap().addAttribute("tab", "treatments");
            mav.setViewName("redirect:/animal/{id}");
        }
        return mav;
    }

    @Transactional
    @RequestMapping(value = "/animal/{id}/save_safe_zone", method = RequestMethod.POST)
    public ModelAndView saveSafeZone(@PathVariable("id") final int id, final SafeZoneMapForm safeZoneMapForm, final Model model, final HttpServletRequest request)
    {
        String safeZoneForPhoton = "";
        Animal animal = animalService.findByAnimalId(id);
        safeZoneService.deleteByAnimal(animal);
        if(!safeZoneMapForm.getSafeZoneElements().isEmpty()){
            String allElements = safeZoneMapForm.getSafeZoneElements().replace(", ", ";");
            List<String> safeZoneElements = new ArrayList<>(Arrays.asList(allElements.split(",")));
            for (String element : safeZoneElements) {
                String latitude = element.substring(1,element.lastIndexOf(";"));
                String longitude = element.substring(element.lastIndexOf(";")+1, element.length()-1);
                SafeZone safeZone = new SafeZone();
                safeZone.setAnimal(animal);
                safeZone.setLatitude(Double.parseDouble(latitude));
                safeZone.setLongitude(Double.parseDouble(longitude));
                safeZoneForPhoton += (latitude + "," + longitude + ";");
                particleAPIService.sendMessageToPhoton(animal.getGpsLocator(), safeZoneForPhoton, "safeZone");
                safeZoneService.save(safeZone);
            }
        }
        if(safeZoneMapForm.getAnotherAnimal()) {
            for (SafeZone safeZone : safeZoneService.findByAnimal(safeZoneMapForm.getAnimalZone())) {
                SafeZone newSafeZone = new SafeZone();
                newSafeZone.setAnimal(animal);
                newSafeZone.setLatitude(safeZone.getLatitude());
                newSafeZone.setLongitude(safeZone.getLongitude());
                safeZoneForPhoton += (safeZone.getLatitude().toString() + "," + safeZone.getLongitude().toString() + ";");
                particleAPIService.sendMessageToPhoton(animal.getGpsLocator(), safeZoneForPhoton, "safeZone");
                safeZoneService.save(newSafeZone);
            }
        }
        animal.setLastNotification(LocalDateTime.now().minusMinutes(animal.getUser().getDelay()));
        animalService.update(animal);
        mav.getModelMap().addAttribute("tab", "safe_zone");
        mav.setViewName("redirect:/animal/{id}");
        return mav;
    }

    @RequestMapping(value = "/edit_animal/{id}", params = "cancel", method = RequestMethod.POST)
    public ModelAndView cancelEditAnimal(HttpServletRequest request) {
        mav.setViewName("redirect:/animal/{id}");
        return mav;
    }


    @RequestMapping(value = "/edit_animal/{id}", method = RequestMethod.GET)
    public ModelAndView editAnimal(@PathVariable("id") final int id, final Model model, final HttpServletRequest request)
    {
        List<Color> colors = colorService.findAll();
        List<Breed> breeds = breedService.findAll();
        List<Species> speciesList = speciesService.findAllByOrderByNameAsc();
        final int active = TabNavigationResolver.resolveActive(request.getParameter("tab"));
        mav.getModelMap().addAttribute("tab", active);
        Animal animal = animalService.findByAnimalId(id);
        if(userResolver.authUser().equals(animal.getUser())) {
            EditAnimalForm editAnimalForm = new EditAnimalForm();
            editAnimalForm.setName(animal.getAnimalName());
            editAnimalForm.setBirthday(animal.getAnimalBirthdayString());
            editAnimalForm.setSpecies(animal.getSpecies().getName());
            editAnimalForm.setBreed(animal.getBreed().getName());
            editAnimalForm.setColor(animal.getColor().getName());
            editAnimalForm.setSex(animal.getAnimalSex());
            editAnimalForm.setTransponderCode(animal.getAnimalTransponderCode());
            editAnimalForm.setTransponderLocation(animal.getAnimalTransponderLocation());
            editAnimalForm.setPassport(animal.getAnimalPassportCode());
            mav.getModelMap().addAttribute("editAnimalForm", editAnimalForm);
            mav.getModelMap().addAttribute("colors", colors);
            mav.getModelMap().addAttribute("breeds", breeds);
            mav.getModelMap().addAttribute("speciesList", speciesList);
            mav.setViewName("/edit_animal");
        } else {
            mav.setViewName("/error");
        }
        return mav;
    }

    @RequestMapping(value = "/animal/{id}/edit_data", method = RequestMethod.POST)
    public ModelAndView editAnimalData(@PathVariable("id") final int id, @RequestParam String action, final EditAnimalForm editAnimalForm, final BindingResult bindingResult, final HttpServletRequest request)
    {
        if(StringUtils.equals(action, "save")) {
            Animal animal = animalService.findByAnimalId(id);
            animal.setAnimalName(editAnimalForm.getName());
            animal.setAnimalBirthday(animal.getAnimalBirthdayFromString(editAnimalForm.getBirthday().replace("/", ".") + " 00:00"));
            animal.setAnimalSex(editAnimalForm.getSex());
            animal.setAnimalTransponderCode(editAnimalForm.getTransponderCode());
            animal.setAnimalTransponderLocation(editAnimalForm.getTransponderLocation());
            animal.setAnimalPassportCode(editAnimalForm.getPassport());
            if(ObjectUtils.isEmpty(colorService.findByName(editAnimalForm.getColor()))) {
                Color color = new Color();
                color.setName(editAnimalForm.getColor());
                colorService.save(color);
            }
            if(ObjectUtils.isEmpty(speciesService.findByName(editAnimalForm.getSpecies()))) {
                Species species = new Species();
                species.setName(editAnimalForm.getSpecies());
                speciesService.save(species);
            }
            if(ObjectUtils.isEmpty(breedService.findByName(editAnimalForm.getBreed()))) {
                Breed breed = new Breed();
                breed.setName(editAnimalForm.getBreed());
                breedService.save(breed);
            }
            animal.setSpecies(speciesService.findByName(editAnimalForm.getSpecies()));
            animal.setBreed(breedService.findByName(editAnimalForm.getBreed()));
            animal.setColor(colorService.findByName(editAnimalForm.getColor()));
            animalService.update(animal);
        }
        mav.getModelMap().addAttribute("tab", "basic_info");
        mav.setViewName("redirect:/animal/{id}");
        return mav;
    }

    @RequestMapping(value = "/edit_treatment/{id}", method = RequestMethod.GET)
    public ModelAndView editTreatment(@PathVariable("id") final int id, final Model model, final HttpServletRequest request)
    {
        final int active = TabNavigationResolver.resolveActive(request.getParameter("tab"));
        mav.getModelMap().addAttribute("tab", active);
        Treatment treatment = treatmentService.findByTreatmentId(id);
        if(userResolver.authUser().equals(treatment.getAnimal().getUser())) {
            TreatmentForm treatmentForm = new TreatmentForm();
            treatmentForm.setTreatmentType(treatment.getTreatmentType());
            treatmentForm.setTreatmentDescription(treatment.getTreatmentDescription());
            if (treatment.getTreatmentAlertTime() != null) {
                treatmentForm.setAlertTime(treatment.getTreatmentAlertTimeString());
            }
            treatmentForm.setDateTime(treatment.getTreatmentDateString());
            treatmentForm.setNotification(treatment.getTreatmentNotification());
            mav.getModelMap().addAttribute("treatmentId", treatment.getTreatmentId());
            mav.getModelMap().addAttribute("treatmentForm", treatmentForm);
            mav.setViewName("/edit_treatment");
        } else {
            mav.setViewName("/error");
        }
        return mav;
    }

    @RequestMapping(value = "/animal/{id}/edit_treatment/{treatment_id}", method = RequestMethod.POST)
    public ModelAndView editAnimalTreatment(@PathVariable("id") final int id, @PathVariable("treatment_id") final int treatment_id, @RequestParam String action, final TreatmentForm treatmentForm, final BindingResult bindingResult, final HttpServletRequest request)
    {
        if(StringUtils.equals(action, "save")) {
            ValidationUtils.invokeValidator(treatmentValidator, treatmentForm, bindingResult);
            if (bindingResult.hasErrors()) {
                mav.getModelMap().addAttribute("treatmentForm", treatmentForm);
                mav.getModelMap().addAttribute("tab", "treatments");
                mav.setViewName("/edit_treatment");
            } else {
                Treatment treatment = treatmentService.findByTreatmentId(treatment_id);
                treatment.setTreatmentType(treatmentForm.getTreatmentType());
                treatment.setTreatmentDescription(treatmentForm.getTreatmentDescription());
                treatment.setTreatmentDate(treatment.getTreatmentDateFromString(treatmentForm.getDateTime().replace("/", ".") + " 00:00"));
                if (!treatmentForm.getAlertTime().isEmpty()) {
                    treatment.setTreatmentAlertTime(treatment.getTreatmentAlertTimeFromString(treatmentForm.getAlertTime().replace("/", ".")));
                    treatment.setTreatmentNotification(treatmentForm.getNotification());
                }
                if (!treatment.getTreatmentNotification()) {
                    treatment.setTreatmentAlertTime(null);
                }
                treatmentService.update(treatment);
            }
        }
        mav.getModelMap().addAttribute("tab", "treatments");
        mav.setViewName("redirect:/animal/{id}");
        return mav;
    }


    @RequestMapping(value = "/animal/{id}/led", method = RequestMethod.GET)
    public ModelAndView editAnimalData(@PathVariable("id") final int id, final HttpServletRequest request)
    {
        if(userResolver.authUser().equals(animalService.findByAnimalId(id).getUser())) {
            GpsLocator gpsLocator = gpsLocatorService.findByGpsLocatorId(animalService.findByAnimalId(id).getGpsLocator().getGpsLocatorId());
            gpsLocator.setLed(!gpsLocator.getLed());
            gpsLocatorService.update(gpsLocator);
            particleAPIService.sendMessageToPhoton(gpsLocator, gpsLocator.getLed().toString().toUpperCase(), "light");
            mav.getModelMap().addAttribute("tab", "basic_info");
            mav.setViewName("redirect:/animal/{id}");
        } else {
            mav.setViewName("/error");
        }
        return mav;
    }

    @RequestMapping(value = "/animal/{id}/download_treatment", method = RequestMethod.POST)
    public void downloadTreatment(@PathVariable("id") final int id, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        Animal animal = animalService.findByAnimalId(id);
        List<Treatment> treatments = treatmentService.findByAnimalAndTreatmentDateAfter(animal,LocalDateTime.now().minusMonths(1).minusDays(1));
        String file = pdfCreatorService.createTreatmentReport(treatments);
        if (!StringUtils.isEmpty(file))
        {
            downloadFile(file, response);
        }
    }

    @RequestMapping(value = "/animal/{id}/download_statistic", method = RequestMethod.POST)
    public void downloadStatistics(@PathVariable("id") final int id, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        Animal animal = animalService.findByAnimalId(id);
        List<AnimalStatistic> statistics = animalStatisticService.findByAnimalAndCreatedTimeAfterOrderByCreatedTimeAsc(animal,LocalDateTime.now().minusMonths(1).minusDays(1));
        String file = pdfCreatorService.createStatisticReport(statistics);
        if (!StringUtils.isEmpty(file))
        {
            downloadFile(file, response);
        }
    }

    private void downloadFile(final String pdf_path, final HttpServletResponse response) throws IOException
    {
        try (final BufferedInputStream input = new BufferedInputStream(new FileInputStream(pdf_path), 10240))
        {
            response.reset();
            response.setHeader("Content-type", "application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + pdf_path);
            response.setHeader("pragma", "public");

            try (final BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream(), 10240))
            {
                final byte[] buffer = new byte[10240];
                int length;
                while ((length = input.read(buffer)) != -1)
                {
                    output.write(buffer, 0, length);
                }

                output.flush();
            }
        }
    }

}