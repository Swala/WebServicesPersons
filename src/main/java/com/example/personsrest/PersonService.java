package com.example.personsrest;

import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.PersonEntity;
import com.example.personsrest.domain.PersonRepository;
import com.example.personsrest.domain.PersonRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;


@Service
@AllArgsConstructor
public class PersonService implements PersonRepository{ //implements PersonRepository

    PersonController personController;
    PersonRepositoryImpl personRepository;

    HashMap<String, Person> persons = new HashMap<>(); //ID om key?
    List<String> groups = new ArrayList<>();

    public PersonService() {

    }

    public List<Person> all(){
        //PersonEntity person1 = new PersonEntity("Test Namn", "test", 33, "city", false, groups);
        //save(person1);
        //return personRepositoryImpl.findAll().stream();

        return findAll();
    }

    public PersonEntity createPerson(){
        PersonEntity personEntity = new PersonEntity();

        return personEntity;
    }


    @Override
    public Optional<Person> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Person> findAll() {
        List<Person> personsList = new ArrayList<Person>(persons.values());

        //vad ska returneras härifrån?
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
