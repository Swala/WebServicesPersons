package com.example.personsrest;

import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.PersonEntity;
import com.example.personsrest.domain.PersonRepository;
import com.example.personsrest.remote.GroupRemote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.List;

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

    public Person createPerson(String name, String city, int age){ //groups??
        PersonEntity personEntity = new PersonEntity(name, city, age);
        //addGroups?
        //System.out.println("personService create: " + name);

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

    public Page<Person> findAllByNameOrCityContaining(String search, int pageNumber, int pageSize) { //removed String name, String city,

        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Person> pagedResult = personRepository.findAllByNameContainingOrCityContaining(search, search, page);

        return pagedResult;

    }

    public Person addGroup(String personId, String groupName) throws PersonNotFoundException {
        //hitta personen som man ska lägga till en grupp på
        Person foundPerson = findPersonById(personId);

        //skapa grupp med groupName, returnerar ID?
        String groupId = groupRemote.createGroup(groupName);
        //String groupId = groupRemote.removeGroup(groupName); //returns the id

        //spara gruppen i personens gruppLista
        foundPerson.addGroup(groupId); // det är ID som ska sparas i listan

        return personRepository.save(foundPerson);
    }


    public Person removeGroup(String id, String groupId) throws PersonNotFoundException {
        //find person with id
        Person foundPerson = findPersonById(id);
        System.out.println("PersonService " + foundPerson.getGroups().get(0));


        for (String groupID : foundPerson.getGroups()){
            System.out.println("ID: " + groupID);
            System.out.println("ID from old test: " + groupId); //groupId är Namn (Ankeborgare) från integration test men id från det andra testet...gör om nåt med DTO return
            if(groupId.length() == 36) {
                foundPerson.removeGroup(groupID);
                return personRepository.save(foundPerson);
            }else if(groupRemote.getNameById(groupID).equals(groupId)) {
                foundPerson.removeGroup(groupID);
                return personRepository.save(foundPerson);
            }
        }

        /*
        //if groupid equals a group in foundPersons groupList, remove it
        for (String groupID : foundPerson.getGroups()){
            System.out.println("ID: " + groupID);
            System.out.println("ID from old test: " + groupId); //groupId är Namn (Ankeborgare) från integration test men id från det andra testet...gör om nåt med DTO return

            //work for integration test since I get the name as param
            if(groupRemote.getNameById(groupID).equals(groupId)){
                foundPerson.removeGroup(groupID);
                return personRepository.save(foundPerson);
            }

            //work for original test since I get the groupId as param
            if(groupID.equals(groupId)){
                foundPerson.removeGroup(groupID);
                return personRepository.save(foundPerson);
            }
            //System.out.println(groupRemote.getNameById(groupID)); //här blir det null i 'vanliga' testet
            //foundPerson.removeGroup(groupID); //grönt test men här skulle alla grupper tas bort men check returnerar null


        };*/

        return foundPerson;

    }
}
