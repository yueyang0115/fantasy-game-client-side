package com.example.fantasyclient.json;

import org.json.JSONObject;

public class Monster {

    private int id;
    private String type;
    private int hp;
    private int atk;
    private Territory territory;

    public Monster(){
    }

    public Monster(String type,int hp,int atk){
        this.type = type;
        this.hp = hp;
        this.atk = atk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }
}

