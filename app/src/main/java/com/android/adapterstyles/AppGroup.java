package com.android.adapterstyles;

import java.util.ArrayList;

public class AppGroup {

    String id, name, photo;
    ArrayList<AppUser> users;

    public AppGroup(){
        this.id= this.name= this.photo = "";
        this.users = new ArrayList<>();
    }

    public AppGroup(String id, String name, String photo, ArrayList<AppUser> users) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<AppUser> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<AppUser> users) {
        this.users = users;
    }
}
