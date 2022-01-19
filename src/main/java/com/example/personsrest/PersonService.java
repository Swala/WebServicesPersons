package com.example.personsrest;

import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.PersonEntity;
import com.example.personsrest.domain.PersonRepository;
import com.example.personsrest.remote.GroupRemote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    PersonRepository personRepository;
    GroupRemote groupRemote;

    public PersonService(PersonRepository personRepository, GroupRemote groupRemote) {

        this.personRepository = personRepository;
        this.groupRemote = groupRemote;
    }

    public List<Person> all(){
        //PersonEntity person1 = new PersonEntity("Test Namn", "test", 33, "city", false, groups);
        //save(person1);
        return personRepository.findAll();
    }

    public Person createPerson(String name, String city, int age){
        PersonEntity personEntity = new PersonEntity(name, city, age);

        return personRepository.save(personEntity);
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

    public Person addGroup(String personId, String groupName) throws PersonNotFoundException {
        //lägg till grupper på hittat person, ska hämtas från remote API? JWT behövs
        //https://groups.edu.sensera.se/webjars/swagger-ui/index.html#/group-controller


        //hitta personen som man ska lägga till en grupp på
        Person foundPerson = findPersonById(personId);

        //skapa grupp med groupName, returnerar ID?
        String groupId = groupRemote.createGroup(groupName);

        //spara gruppen i personens gruppLista
        String groupNameFromRemote = groupRemote.getNameById(groupId);
        System.out.println("from personService " + groupNameFromRemote);
        foundPerson.addGroup(groupId);
        //foundPerson.addGroup(groupRemote.getNameById(groupId)); //ska det sparas gruppID eller gruppNamn??

        return personRepository.save(foundPerson);

    }

    public Page<Person> findAllByNameOrCityContaining(String search, int pageNumber, int pageSize) { //removed String name, String city,

        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Person> pagedResult = personRepository.findAllByNameContainingOrCityContaining(search, search, page);

        return pagedResult;

    }

    public void removeGroup(String id, String groupName) throws PersonNotFoundException {
        //find person with id
       Person person = findPersonById(id);


        //remove the group by groupName


    }
}
