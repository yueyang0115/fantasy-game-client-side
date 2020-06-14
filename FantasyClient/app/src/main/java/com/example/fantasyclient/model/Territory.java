package com.example.fantasyclient.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Territory implements Serializable {

    private WorldCoord coord;

    private String status;

    private String terrainType;

    private Building building;

    public Territory(){
    }

    public Territory(WorldCoord coord){
        this.coord = coord;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object e) {
        if ( e instanceof Territory &&
                this.coord == ((Territory)e).getCoord())
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        Integer tempID = this.coord.getWid();
        return tempID.hashCode();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WorldCoord getCoord() {
        return coord;
    }

    public void setCoord(WorldCoord coord) {
        this.coord = coord;
    }

    public String getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(String terrainType) {
        this.terrainType = terrainType;
    }
}
