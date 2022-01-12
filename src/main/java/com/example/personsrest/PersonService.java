package com.example.personsrest;

import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.PersonEntity;
import com.example.personsrest.domain.PersonRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.List;

@Service
public class PersonService {

    PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> all(){
        //PersonEntity person1 = new PersonEntity("Test Namn", "test", 33, "city", false, groups);
        //save(person1);
        return personRepository.findAll();
    }

    public PersonEntity createPerson(){
        PersonEntity personEntity = new PersonEntity();

        return personEntity;
    }

    public Person findPersonById(String id) throws PersonNotFoundException {
        Optional<Person> optionalPerson = personRepository.findById(id);
        Person person= optionalPerson.get();

        return person;
    }
}
