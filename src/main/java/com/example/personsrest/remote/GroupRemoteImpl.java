package com.example.personsrest.remote;


import com.example.personsrest.domain.Person;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

public class GroupRemoteImpl implements GroupRemote{

    HashMap<String, Group> groups = new HashMap<>(); //ID som key
    private static final String BASE_URL = "https://groups.edu.sensera.se/api/groups/";

    WebClient webClient = WebClient.create();

    public Mono<Group> get(String id) {
        return webClient.get().uri(BASE_URL + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(Group.class);
    }

    public Mono<Group> create(String name) {
        return webClient.post().uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new CreateGroup(name)))
                .retrieve().bodyToMono(Group.class);
    }



    @Override
    public String getNameById(String groupId) { //Group object innehåller id och name
        //annvänd groupId för att hitta och returnera name
        System.out.println(groupId);
        String group = get(groupId).subscribe().toString();
        System.out.println(group);
        //String id = group.toString().getName();

        return group;
    }

    @Override
    public String createGroup(String name) {
        String groupId = create(name).subscribe().toString();

        return groupId;
    }

    @Override
    public String removeGroup(String name) {
        return null;
    }

    @Value
    static class Group {
        String id;
        String name;

        @JsonCreator
        public Group(
                @JsonProperty("id") String id,
                @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Value
    static class CreateGroup {
        String name;
    }


}
