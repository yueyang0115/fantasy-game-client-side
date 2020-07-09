package com.example.fantasyclient.model;

import java.io.Serializable;

public class Skill implements Serializable {
    private String name;
    private int attack;

    public Skill(){}

    public Skill(String name, int attack) {
        this.name = name;
        this.attack = attack;
    }

    public String getName() {
            return name;
        }

    public void setName(String name) {
            this.name = name;
        }

    public int getAttack() {
            return attack;
        }

    public void setAttack(int attack) {
            this.attack = attack;
        }

}
