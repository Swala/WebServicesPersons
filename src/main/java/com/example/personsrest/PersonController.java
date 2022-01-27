package com.example.personsrest;

import com.example.personsrest.domain.CreatePerson;
import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.UpdatePerson;
import com.example.personsrest.remote.GroupRemote;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    PersonService personService;
    GroupRemote groupRemote; //vill inte ha detta här...

    @GetMapping()
    public List<PersonDTO> all(@RequestParam(name="search", required = false) String search,
                                @RequestParam(name="pagenumber", required = false) Integer pageNumber,
                               @RequestParam(name="pagesize", required = false) Integer pagesize){ //add pagenumber and size

        if(search != null) {
            //System.out.println(search);
            return personService.findAllByNameOrCityContaining(search, pageNumber, pagesize)
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
    public ResponseEntity<PersonDTO> createNewPerson(@RequestBody CreatePerson createPerson){
        //System.out.println(createPerson.getName());
        try {
            return ResponseEntity.ok(toDTO(personService.createPerson(
                    createPerson.getName(),
                    createPerson.getCity(),
                    createPerson.getAge())));
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
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

    @PutMapping("/{id}/addGroup/{groupName}")
    public ResponseEntity<PersonDTO> addGroup(@PathVariable("id") String id,@PathVariable("groupName") String groupName){
        try{
            return ResponseEntity.ok(toDTO(personService.addGroup(id, groupName)));

        }catch (PersonNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/removeGroup/{groupId}") //or groupName...
    public ResponseEntity<PersonDTO> removeGroupFromPerson(@PathVariable("id") String id, @PathVariable("groupId") String groupId) {
        try{
            return ResponseEntity.ok(toDTO(personService.removeGroup(id, groupId)));

        }catch (PersonNotFoundException e){
            return ResponseEntity.notFound().build();
        }

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

        //add method getGroups?? should handle groupId to name in service

    }

    public PersonDTO toDTO(Person person){
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getCity(),
                person.getAge(),
                person.getGroups().stream().map(id ->  groupRemote.getNameById(id)).collect(Collectors.toList())//handle null exeption

        );



    }
}
