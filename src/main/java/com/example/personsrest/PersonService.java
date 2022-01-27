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
        return personRepository.findAll();
    }

    public Person createPerson(String name, String city, int age){
        PersonEntity personEntity = new PersonEntity(name, city, age);
        return personRepository.save(personEntity);
    }

    public Person findPersonById(String id) throws PersonNotFoundException { //or will Optional throw exception for me??
        Optional<Person> optionalPerson = Optional.ofNullable(personRepository.findById(id).orElseThrow(
                () -> new PersonNotFoundException(id)));

        return optionalPerson.get();
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

    public Page<Person> findAllByNameOrCityContaining(String search, int pageNumber, int pageSize) {

        Pageable page = PageRequest.of(pageNumber, pageSize);
        return personRepository.findAllByNameContainingOrCityContaining(search, search, page); //dont know if search is by city or name
    }

    public Person addGroup(String personId, String groupName) throws PersonNotFoundException {
        //hitta personen som man ska lägga till en grupp på
        Person foundPerson = findPersonById(personId);

        //skapa grupp med groupName
        String groupId = groupRemote.createGroup(groupName);

        //spara gruppen i personens gruppLista
        foundPerson.addGroup(groupId); // det är ID som ska sparas i listan

        return personRepository.save(foundPerson);
    }


    //separate method to removeByName
    public Person removeGroup(String id, String groupId) throws PersonNotFoundException {

        Person foundPerson = findPersonById(id);

        for (String groupID : foundPerson.getGroups()){
            if(groupId.length() == 36 && groupId.contains("-")) { //groupId.length() == 36 or groupId.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")
                foundPerson.removeGroup(groupID);
                return personRepository.save(foundPerson);
            }else if(groupRemote.getNameById(groupID).equals(groupId)) {
                foundPerson.removeGroup(groupID);
                return personRepository.save(foundPerson);
            }
        }
        return foundPerson;
    }
}
