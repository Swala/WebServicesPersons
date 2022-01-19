package com.example.personsrest;

import com.example.personsrest.domain.CreatePerson;
import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.PersonEntity;
import com.example.personsrest.domain.UpdatePerson;
import com.example.personsrest.remote.GroupRemote;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    PersonService personService;
    GroupRemote groupRemote;

    @GetMapping()
    public List<PersonDTO> all(@RequestParam(required = false) String search){

        //System.out.println("hej från All-metoden");
        if(search != null) {
            System.out.println(search);
            return personService.findAllByNameOrCityContaining(search, 0, 10)
                    .map(this::toDTO).stream().collect(Collectors.toList());
        }else {

            return personService.all().stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findPersonById(@PathVariable("id") String id){
        try {
            return ResponseEntity.ok(toDTO(personService.findPersonById(id)));
        }catch (PersonNotFoundException e){
            return ResponseEntity.notFound()
                    .build();
        }
    }

    @PostMapping
    public PersonDTO createNewPerson(@RequestBody CreatePerson createPerson){
        return toDTO(personService.createPerson(
                createPerson.getName(),
                createPerson.getCity(),
                createPerson.getAge()
        ));

    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable("id") String id, @RequestBody UpdatePerson updatePerson){
        try{
            return ResponseEntity.ok(toDTO(personService.updatePerson(id,
                    updatePerson.getName(),
                    updatePerson.getCity(),
                    updatePerson.getAge())));

        }catch (PersonNotFoundException e){
            return ResponseEntity.notFound()
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable("id") String id){
            personService.deletePerson(id);

    }

    /*
    @GetMapping ("search")  // "{search} optional path variable?
    public List<PersonDTO> findAllByNameOrCityContaining( //
            @RequestParam(name = "search") String search,
            @RequestParam(name = "pageNumber")int pageNumber,
            @RequestParam(name = "pageSize") int pageSize){

            //System.out.println("controller " + pageRequest.getPageNumber());
            System.out.println(search);

            //Page<Person> list = personService.findAllByNameOrCityContaining(name.get(), city.get(), pageRequest); //vad behövs skickas med här?
            //Page<Person> list = personService.findAllByNameOrCityContaining(pageable);

            return personService.findAllByNameOrCityContaining(search, pageNumber, pageSize)
                    .map(this::toDTO).stream().collect(Collectors.toList());

    }*/


    @PutMapping("/{id}/addGroup/{groupName}") //enligt beskrivning ska groupname inte vara med?
    public ResponseEntity<PersonDTO> addGroup(@PathVariable("id") String id,@PathVariable("groupName") String groupName){
        try{
            return ResponseEntity.ok(toDTO(personService.addGroup(id, groupName))); //måste ändra getGroups, via GroupRemote?

        }catch (PersonNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/removeGroup/{groupName}")
    public void removeGroupFromPerson(@PathVariable("id") String id, @PathVariable("groupName") String groupName) throws PersonNotFoundException {
        personService.removeGroup(id, groupName);

    }

    @Value
    class PersonDTO {
        String id;
        String name;
        String city;
        int age;
        List<String> groups;

        @JsonCreator
        public PersonDTO(
                @JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("city") String city,
                @JsonProperty("age") int age,
                @JsonProperty("groups") List<String> groups) {
            this.id = id;
            this.name = name;
            this.city = city;
            this.age = age;
            this.groups = groups;
        }

    }

    public PersonDTO toDTO(Person person){
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getCity(),
                person.getAge(),
                person.getGroups().stream().map(id -> groupRemote.getNameById(id)).collect(Collectors.toList())
        );


    }
}
