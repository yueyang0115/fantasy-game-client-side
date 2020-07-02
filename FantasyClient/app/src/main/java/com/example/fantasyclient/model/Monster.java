package com.example.fantasyclient.model;

public class Monster extends Unit{

    private WorldCoord coord;

    private boolean needUpdate;

    public Monster() {
    }

    public Monster(String type, String name, int hp, int atk, int speed, WorldCoord coord, boolean needUpdate) {
        super(type, name, hp, atk, speed);
        this.coord = coord;
        this.needUpdate = needUpdate;
    }

    public WorldCoord getCoord() {
        return coord;
    }

    public void setCoord(WorldCoord coord) {
        this.coord = coord;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }
}

