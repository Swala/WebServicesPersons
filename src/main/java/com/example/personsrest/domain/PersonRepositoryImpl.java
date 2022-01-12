package com.example.personsrest.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class PersonRepositoryImpl implements PersonRepository {

   // List<PersonEntity> persons2 = new ArrayList<>();
    List<String> groups = new ArrayList<>();
    HashMap<String, Person> persons = new HashMap<>(); //ID om key?


    @Override
    public Optional<Person> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Person> findAll() {
        List<Person> personsList = new ArrayList<Person>(persons.values());
        return personsList;
    }

    @Override
    public Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Person save(Person person) {
        persons.put(person.getId(), person);
        return person;
    }

    @Override
    public void delete(String id) {

    }
}
