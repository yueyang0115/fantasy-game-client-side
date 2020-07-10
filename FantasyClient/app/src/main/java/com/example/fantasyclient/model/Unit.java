package com.example.fantasyclient.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Unit implements Serializable {

    private int id;
    private String type;
    private String name;
    private int hp;
    private int atk;
    private int speed;
    private int level;
    private Set<Skill> skills = new HashSet<>();

    public Unit(){
    }

    public Unit(String type, String name, int hp, int atk, int speed, int level, Set<Skill> skills){
        this.type = type;
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.speed = speed;
        this.level = level;
        this.skills = skills;
    }

    public Unit(Unit unit){
        setFields(unit);
    }

    public void setFields(Unit unit){
        this.id = unit.getId();
        this.type = unit.getType();
        this.name = unit.getName();
        this.hp = unit.getHp();
        this.atk = unit.getAtk();
        this.speed = unit.getSpeed();
        this.level = unit.getLevel();
        this.skills = skills;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public Set<Skill> getSkills() { return skills; }

    public void setSkills(Set<Skill> skills) { this.skills = skills; }

    @Override
    public boolean equals(Object e) {
        if ( e instanceof Unit &&
                this.id == ((Unit)e).getId())
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        Integer tempID = id;
        return tempID.hashCode();
    }
}
