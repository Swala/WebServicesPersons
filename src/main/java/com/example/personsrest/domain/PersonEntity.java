package com.example.personsrest.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
    private List<String> groups; //contains groupId


    public PersonEntity(String name, String city, int age) {
        this.name = name;
        this.city = city;
        this.age = age;
        this.groups = new ArrayList<>();

    }

    @Override
    public void addGroup(String groupId) {
        groups.add(groupId);
    }

    @Override
    public void removeGroup(String groupId) {
        groups.remove(groupId);
    }
}
