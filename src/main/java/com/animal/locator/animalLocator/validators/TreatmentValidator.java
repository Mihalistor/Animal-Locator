package com.animal.locator.animalLocator.validators;

import com.animal.locator.animalLocator.forms.TreatmentForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component("treatmentValidator")
public class TreatmentValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return TreatmentForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        final TreatmentForm treatmentForm = (TreatmentForm) o;

        if(StringUtils.isEmpty(treatmentForm.getDateTime())) {
            errors.rejectValue("dateTime","treatment.date.time.empty", "Date time is empty");
        }
    }

}