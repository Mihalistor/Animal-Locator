package com.animal.locator.animalLocator.validators;

import com.animal.locator.animalLocator.forms.CreateAnimalForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component("createAnimalValidator")
public class CreateAnimalValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CreateAnimalForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {


        final CreateAnimalForm createAnimalForm = (CreateAnimalForm) o;
        String[] devices = createAnimalForm.getDevices().split(";");
        String[] tokens = createAnimalForm.getTokens().split(";");


        if(StringUtils.isEmpty(createAnimalForm.getDevices())) {
            errors.rejectValue("devices","create.animal.devices.empty", "Devices ID field is empty!");
        }

        if(StringUtils.isEmpty(createAnimalForm.getTokens())) {
            errors.rejectValue("tokens","create.animal.tokens.empty", "Tokens field is empty!");
        }

        if(createAnimalForm.getNumberOfAnimals() != devices.length) {
            errors.rejectValue("devices","create.animal.devices.wrong.size", "Wrong number of devices ID! Must be equal to number of created animal");
        }

        if(createAnimalForm.getNumberOfAnimals() != tokens.length) {
            errors.rejectValue("tokens","create.animal.tokens.wrong.size", "Wrong number of tokens! Must be equal to number of created animal");
        }
    }

}
