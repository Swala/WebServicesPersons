package com.example.personsrest.remote;


public class GroupRemoteImpl implements GroupRemote{

    @Override
    public String getNameById(String groupId) { //Group object innehåller id och name
        //annvänd groupId för att hitta och returnera name från ??

        return null;
    }

    @Override
    public String createGroup(String name) {
        //"https://groups.edu.sensera.se/webjars/swagger-ui/index.html#/group-controller"

        //remoteGroupController.createGroup(name);
        //send name to remote api to create new group med POST
        //System.out.println("from groupRemote " +name);
        return null;
    }

    @Override
    public String removeGroup(String name) {
        return null;
    }
}
