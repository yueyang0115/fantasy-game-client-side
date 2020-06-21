package com.example.fantasyclient.model;

public class Monster extends Unit{

    private WorldCoord coord;

    private boolean needUpdate;

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

