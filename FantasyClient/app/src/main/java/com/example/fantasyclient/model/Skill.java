package com.example.fantasyclient.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Skill implements Serializable {
    private int id;
    private String name;
    private int attack;
    private int requiredLevel;
    private Set<Skill> requiredSkill = new HashSet<>();

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public Set<Skill> getRequiredSkill() {
        return requiredSkill;
    }

    public void setRequiredSkill(Set<Skill> requiredSkill) {
        this.requiredSkill = requiredSkill;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(Skill.class)) {
            Skill s = (Skill) o;
            return this.name.equals(s.name)
                    && this.requiredLevel == s.requiredLevel
                    && this.requiredSkill.equals(s.requiredSkill)
                    && this.requiredSkill.containsAll(s.requiredSkill)
                    && this.attack == s.attack;
        }
        return false;
    }
    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
    @Override
    public String toString(){
        return (this.name + ":" + this.requiredLevel + "," + this.attack);
    }

}
