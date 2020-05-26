package com.example.fantasyclient.json;

import java.util.ArrayList;
import java.util.List;

public class Territory {

    private int id;
    private int wid;
    private double x;
    private double y;
    private String status;
    private List<Monster> monsters = new ArrayList<>();

    public Territory(){
    }

    public Territory(int wid,double x,double y, String status){
        this.wid = wid;
        this.x = x;
        this.y = y;
        this.status = status;
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addMonster(Monster monster){
        monster.setTerritory(this);
        this.monsters.add(monster);
    }
}
