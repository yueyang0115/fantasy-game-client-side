package com.example.fantasyclient.model;

import java.io.Serializable;

public class Terrain implements Serializable {

    private int id;
    private String type;

    public Terrain() {
    }

    public Terrain(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
