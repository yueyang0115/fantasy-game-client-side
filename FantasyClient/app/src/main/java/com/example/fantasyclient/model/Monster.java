package com.example.fantasyclient.model;

import java.util.List;
import java.util.Set;

public class Monster extends Unit{

    private WorldCoord coord;

    private boolean needUpdate;

    public Monster() {
    }

    public Monster(String type, String name, int hp, int atk, int speed, int level, WorldCoord coord, boolean needUpdate, Set<Skill> skills) {
        super(type, name, hp, atk, speed, level, skills);
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

