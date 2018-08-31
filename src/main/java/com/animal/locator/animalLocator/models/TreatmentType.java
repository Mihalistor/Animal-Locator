package com.animal.locator.animalLocator.models;

public enum TreatmentType {

    EXAMINATION("Examination"),
    RABIES("Vaccination against Rabies"),
    TITRATION_TEST("Rabies anitbody Titration test"),
    ECHINOCOCCUS("Anti-Echinococcus treatment"),
    PARASITE("Other Anti-Parasite treatments"),
    OTHER_VACCINATIONS("Other Vaccinations"),
    CLINICAL_EXAMINATION("Clinical Examination");

    private String treatmentType;

    TreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

}