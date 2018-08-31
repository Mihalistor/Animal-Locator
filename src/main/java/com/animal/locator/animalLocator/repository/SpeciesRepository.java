package com.animal.locator.animalLocator.repository;

import com.animal.locator.animalLocator.models.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpeciesRepository extends JpaRepository<Species, Integer> {

    Species findBySpeciesId(int id);
    Species findByName(String name);
    List<Species> findAllByOrderByNameAsc();

}