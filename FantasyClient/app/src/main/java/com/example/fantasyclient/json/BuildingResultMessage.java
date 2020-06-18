package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Building;

import java.util.List;

public class BuildingResultMessage {

    private List<String> buildingList;
    private String result;
    private Building building;

    public BuildingResultMessage() {
    }

    public List<String> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(List<String> buildingList) {
        this.buildingList = buildingList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
