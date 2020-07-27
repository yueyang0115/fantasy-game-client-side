package com.example.fantasyclient.json;

import java.io.Serializable;

public class RedirectMessage implements Serializable {

    private String destination;

    public RedirectMessage() {
    }

    public RedirectMessage(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
