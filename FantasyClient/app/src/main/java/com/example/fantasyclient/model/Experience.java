package com.example.fantasyclient.model;

import java.io.Serializable;

public class Experience implements Serializable {
    private int experience;
    private int level;
    private int skillPoint;

    public Experience(){}

    public Experience(int experience, int level, int skillPoint) {
        this.experience = experience;
        this.level = level;
        this.skillPoint = skillPoint;
    }

    public int getExperience() { return experience; }

    public void setExperience(int experience) { this.experience = experience; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public int getSkillPoint() { return skillPoint; }

    public void setSkillPoint(int skillPoint) { this.skillPoint = skillPoint; }
}
