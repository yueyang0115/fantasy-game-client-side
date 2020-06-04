package com.example.fantasyclient.model;

import java.util.ArrayList;
import java.util.List;

public class Territory {

    private int id;
    private int wid;
    private int x;
    private int y;
    private String status;
    private List<Monster> monsters = new ArrayList<>();
    private Terrain terrain;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    private Building building;

    public Territory(){
    }

    public Territory(int wid,int x,int y, String status){
        this.wid = wid;
        this.x = x;
        this.y = y;
        this.status = status;
    }

    @Override
    public boolean equals(Object e) {
        if ( e instanceof Territory &&
                this.id == ((Territory)e).getId())
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        Integer tempID = id;
        return tempID.hashCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
}
