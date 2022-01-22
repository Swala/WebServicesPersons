package com.example.personsrest.remote;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;

public class GroupRemoteImpl implements GroupRemote{

    HashMap<String, Group> groups = new HashMap<>(); //ID som key

    WebClient webClient;

    private KeyCloakToken token;
    private final String BASE_URL = "https://groups.edu.sensera.se/api/groups";

    public GroupRemoteImpl() {
        this.webClient = WebClient.create();
        this.token = KeyCloakToken.acquire("https://iam.sensera.se/", "test", "group-api", "user", "djnJnPf7VCQvp3Fc")
                .block();
    }


    public Group get(String id) {
        //System.out.println("from get in groupremote " + id); //ska vara id, inte namn
        return webClient.get().uri(BASE_URL + "/"+id)
                .header("Authorization", "Bearer " + token.accessToken)
                .header(HttpHeaders.CONTENT_TYPE , MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Group.class)
                .single()
                .block();
    }


    public Group create(CreateGroup createGroup) {
        //System.out.println("token: " + token.accessToken);
        return webClient.post().uri(BASE_URL)
                .header("Authorization", "Bearer " + token.accessToken)
                .header(HttpHeaders.CONTENT_TYPE , MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(new CreateGroup(createGroup.getName())))
                .retrieve()
                .bodyToMono(Group.class)
                .single()
                .block();

    }

    @Override
    public String getNameById(String groupId) { //Group object innehåller id och name
        //annvänd groupId för att hitta och returnera name
        //System.out.println("groupImpl " + groupId);
        Group group = get(groupId);
        return group.getName();
    }

    @Override
    public String createGroup(String name) {
        Group group = create(new CreateGroup(name));
        groups.put(group.getId(), group);
        //System.out.println("from groupRemote " + group.getName());

        return group.getId();
    }

    @Override
    public String removeGroup(String name) { //ska denna returnera ID??

        return null;
    }



    @Value
    public static class Group {
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
    public static class CreateGroup {
        String name;
    }


}
