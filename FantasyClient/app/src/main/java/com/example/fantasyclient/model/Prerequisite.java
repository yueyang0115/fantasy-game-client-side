package com.example.fantasyclient.model;

import java.io.Serializable;

public class Prerequisite implements Serializable {

    String terrain;

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }
}
