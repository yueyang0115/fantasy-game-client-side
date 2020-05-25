package com.example.fantasyclient.json;

public class ModelBase extends JsonBase {
    private int id;

    public ModelBase(int id, String type) {
        this.type = type;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
