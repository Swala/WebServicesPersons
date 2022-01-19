package com.example.personsrest.domain;

import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonRepositoryImpl implements PersonRepository {

    HashMap<String, Person> persons = new HashMap<>(); //ID om key?

    @Override
    public Optional<Person> findById(String id) {
        Optional<Person> optionalPerson =findById(id);
        return optionalPerson;
    }

    @Override
    public List<Person> findAll() {
        List<Person> personsList = new ArrayList<Person>(persons.values());
        return personsList;
    }

    @Override
    public Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable) {

        System.out.println(persons.size());
        List<Person>personList = findAll()
                .stream()
                .filter(entry -> entry.getName().contains(name) || entry.getCity().contains(city))
                .collect(Collectors.toList());

        System.out.println(personList.size());

        for(Person p : personList){
            System.out.println(p.getName());
        }


        //Page<Person> page = new PageImpl<>(personList, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), personList.size());
        Page<Person> page = new PageImpl<>(personList, pageable, pageable.getPageNumber());

        return page;
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
