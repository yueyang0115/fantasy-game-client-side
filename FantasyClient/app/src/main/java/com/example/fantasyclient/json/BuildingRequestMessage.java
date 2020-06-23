package com.example.fantasyclient.json;

import com.example.fantasyclient.model.WorldCoord;

public class BuildingRequestMessage {

    private WorldCoord coord;
    private String action; //"createList", "upgradeList", "create", "upgrade", "destruct"
    private String buildingName;

    //constructor for lists
    public BuildingRequestMessage(WorldCoord coord, String action) {
        this.coord = coord;
        this.action = action;
    }

    //constructor for build actions
    public BuildingRequestMessage(WorldCoord coord, String action, String buildingName) {
        this.coord = coord;
        this.action = action;
        this.buildingName = buildingName;
    }

    public WorldCoord getCoord() {
        return coord;
    }

    public void setCoord(WorldCoord coord) {
        this.coord = coord;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
