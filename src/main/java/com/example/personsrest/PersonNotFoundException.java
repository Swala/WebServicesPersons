package com.example.personsrest;

public class PersonNotFoundException extends Exception{
    public PersonNotFoundException(String id){ super(id); }
}
