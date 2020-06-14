package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Monster;
import com.example.fantasyclient.model.Territory;

import java.util.List;

public class PositionResultMessage {
    private List<Territory> territoryArray;
    private List<Monster> monsterArray;

    public List<Monster> getMonsterArray() {
        return monsterArray;
    }

    public void setMonsterArray(List<Monster> monsterArray) {
        this.monsterArray = monsterArray;
    }

    public List<Territory> getTerritoryArray() {
        return territoryArray;
    }

    public void setTerritoryArray(List<Territory> territoryArray) {
        this.territoryArray = territoryArray;
    }
}