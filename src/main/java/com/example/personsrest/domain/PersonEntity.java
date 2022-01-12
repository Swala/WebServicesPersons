package com.example.personsrest.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PersonEntity implements Person {

    private String id = UUID.randomUUID().toString();
    private String name;
    private int age;
    private String city;
    private boolean isActive;
    private List<String> groups; //ska lista namn på grupper som personen tillhör


    public PersonEntity(String name, String city, int age) {
        this.name = name;
        this.city = city;
        this.age = age;

    }

    @Override
    public void addGroup(String groupId) {

    }

    @Override
    public void removeGroup(String groupId) {

    }
}
