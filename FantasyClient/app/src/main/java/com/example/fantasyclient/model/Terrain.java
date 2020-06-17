package com.example.fantasyclient.model;

import java.io.Serializable;

public class Terrain implements Serializable {

    private String type;

    public Terrain() {
    }

    public Terrain( String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
