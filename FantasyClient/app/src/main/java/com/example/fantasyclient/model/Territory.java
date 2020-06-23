package com.example.fantasyclient.model;

import java.io.Serializable;

public class Territory implements Serializable {

    private WorldCoord coord;

    private int tame;

    private String terrainType;

    public Territory(){
    }

    public Territory(WorldCoord coord, int tame){
        this.coord = coord;
        this.tame = tame;
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

    public WorldCoord getCoord() {
        return coord;
    }

    public void setCoord(WorldCoord coord) {
        this.coord = coord;
    }

    public int getTame() {
        return tame;
    }

    public void setTame(int tame) {
        this.tame = tame;
    }

    public void updateCoord(WorldCoord coord){
        this.coord.setY(coord.getY());
        this.coord.setX(coord.getX());
    }

    public String getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(String terrainType) {
        this.terrainType = terrainType;
    }
}
