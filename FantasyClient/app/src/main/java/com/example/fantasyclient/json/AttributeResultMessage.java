package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Soldier;

import java.io.Serializable;
import java.util.List;

public class AttributeResultMessage implements Serializable {

    List<Soldier> soldiers;

    public AttributeResultMessage() {
    }

    public AttributeResultMessage(List<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    public List<Soldier> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(List<Soldier> soldiers) {
        this.soldiers = soldiers;
    }
}
