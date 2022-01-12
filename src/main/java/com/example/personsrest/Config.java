package com.example.personsrest;

import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.PersonRepository;
import com.example.personsrest.domain.PersonRepositoryImpl;
import com.example.personsrest.remote.GroupRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

//Får ändra i denna
@Configuration
public class Config {
    @Bean
    public GroupRemote groupRemote() {
        return null;
    }

    @Bean
    public PersonRepository personRepository() {

        //return new PersonService(); //skapas redan eftersom service är annoterad med @Service
        return new PersonRepositoryImpl();
    }
}
