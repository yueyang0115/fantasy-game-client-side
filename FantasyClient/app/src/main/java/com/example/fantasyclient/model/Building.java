package com.example.fantasyclient.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Building implements Serializable {

    private int id;

    private String name;

    @JsonBackReference
//    @JsonIgnore
    private Territory territory;

    public Building() {
    }

    public Building(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }
}


