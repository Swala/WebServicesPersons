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

    public PersonEntity createPerson(String name, String city, int age){
        PersonEntity personEntity = new PersonEntity(name, city, age);
        personRepository.save(personEntity);

        return personEntity;
    }

    public Person findPersonById(String id) throws PersonNotFoundException {
        Optional<Person> optionalPerson = Optional.ofNullable(personRepository.findById(id).orElseThrow(
                () -> new PersonNotFoundException(id)));

        Person person = optionalPerson.get();

        return person;
    }

    public Person updatePerson(String id, String name, String city, int age) throws PersonNotFoundException {
        Person person = findPersonById(id);
        person.setName(name);
        person.setCity(city);
        person.setAge(age);

        return personRepository.save(person);
    }

    public void deletePerson(String id) {
        //check findById first?
        personRepository.delete(id);

    }
}
