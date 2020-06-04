package com.example.fantasyclient.model;

import java.util.ArrayList;
import java.util.List;


public abstract class Building {

    private int id;

    private String name;

    private List<Territory> territories = new ArrayList<>();

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

    public List<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(List<Territory> territories) {
        this.territories = territories;
    }

    public void addTerritory(Territory territory) {
        this.territories.add(territory);
    }
}


