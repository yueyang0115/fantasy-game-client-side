package com.example.fantasyclient.fragment;

import com.example.fantasyclient.model.Skill;
import com.example.fantasyclient.model.Unit;

public interface ElementSelector{
    void doWithSelectedSkill(Skill skill);
    void doWithSelectedUnit(Unit unit);
}
