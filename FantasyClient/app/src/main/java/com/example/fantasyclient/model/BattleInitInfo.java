package com.example.fantasyclient.model;

import java.io.Serializable;
import java.util.List;

public class BattleInitInfo implements Serializable {
    private List<Monster> monsters;  //all monsters in the territory
    private List<Soldier> soldiers;  //all soldiers the player has
    private List<Integer> units;

    public BattleInitInfo() {
    }

    public BattleInitInfo(List<Monster> monsters, List<Soldier> soldiers, List<Integer> units) {
        this.monsters = monsters;
        this.soldiers = soldiers;
        this.units = units;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public List<Soldier> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(List<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    public List<Integer> getUnits() {
        return units;
    }

    public void setUnits(List<Integer> units) {
        this.units = units;
    }
}
