package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Building;

import java.io.Serializable;
import java.util.List;

public class BuildingResultMessage implements Serializable {

    private List<Building> buildingList;
    private String result;
    private String action;//"createList", "upgradeList", "create", "upgrade", "destruct"
    private Building building;

    public BuildingResultMessage() {
    }

    public BuildingResultMessage(String result) {
        this.result = result;
    }

    public List<Building> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(List<Building> buildingList) {
        this.buildingList = buildingList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
