package com.example.personsrest;

import com.example.personsrest.domain.CreatePerson;
import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.UpdatePerson;
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

    @GetMapping
    public List<PersonDTO> all(){
        return personService.all().stream().map(PersonController::toDTO).collect(Collectors.toList());
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

    @Value
    static class PersonDTO {
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

    public static PersonDTO toDTO(Person person){
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getCity(),
                person.getAge(),
                person.getGroups()
        );


    }
}
