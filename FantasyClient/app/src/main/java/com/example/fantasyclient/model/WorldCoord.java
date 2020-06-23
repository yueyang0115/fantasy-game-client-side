package com.example.fantasyclient.model;

import java.io.Serializable;

public class WorldCoord implements Serializable{

    private int wid;
    private int x;
    private int y;


    public WorldCoord() { }

    public WorldCoord(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public WorldCoord(WorldCoord coord){
        this.wid = coord.wid;
        this.x = coord.getX();
        this.y = coord.getY();
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

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(WorldCoord.class)) {
            WorldCoord wc = (WorldCoord) o;
            return x==wc.x && y==wc.y;
        }
        return false;
    }
    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
    @Override
    public String toString(){
        return (x + "," + y);
    }

}
