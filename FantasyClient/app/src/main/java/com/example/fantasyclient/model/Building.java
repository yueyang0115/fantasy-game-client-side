package com.example.fantasyclient.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Building implements Serializable {

    private WorldCoord coord;

    private String name;

    private int cost;

    private List<Prerequisite> prerequisites;

    private Map<String, Building> UpgradeTo;

    public Building() {
    }

    public Building(String name) {
        this.name = name;
    }



    public WorldCoord getCoord() {
        return coord;
    }

    public void setCoord(WorldCoord coord) {
        this.coord = coord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Prerequisite> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Prerequisite> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Map<String, Building> getUpgradeTo() {
        return UpgradeTo;
    }

    public void setUpgradeTo(Map<String, Building> upgradeTo) {
        UpgradeTo = upgradeTo;
    }

    @Override
    public boolean equals(Object b) {
        if ( b instanceof Building &&
                this.coord == ((Building)b).getCoord())
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return coord.hashCode();
    }
}


