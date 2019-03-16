package com.boss078.petbook;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PetRepository extends CrudRepository<Pet, Integer> {

}