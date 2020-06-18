package com.example.fantasyclient.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Building implements Serializable {

    private int id;

    private String name;

    private WorldCoord coord;

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

    public WorldCoord getCoord() {
        return coord;
    }

    public void setCoord(WorldCoord coord) {
        this.coord = coord;
    }

    @Override
    public boolean equals(Object b) {
        if ( b instanceof Building &&
                this.id == ((Building)b).getId())
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        Integer tempID = id;
        return tempID.hashCode();
    }
}


