package com.example.personsrest.domain;

import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonRepositoryImpl implements PersonRepository {

    HashMap<String, Person> persons = new HashMap<>(); //Key = ID

    @Override
    public Optional<Person> findById(String id) {
        return Optional.ofNullable(persons.get(id));
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(persons.values());
    }

    @Override
    public Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable) {
        List<Person>personList = findAll()
                .stream()
                .filter(entry -> entry.getName().contains(name) || entry.getCity().contains(city)) //&&?
                .collect(Collectors.toList());

        return new PageImpl<>(personList, pageable, pageable.getPageNumber());
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
        persons.remove(id);
    }
}
