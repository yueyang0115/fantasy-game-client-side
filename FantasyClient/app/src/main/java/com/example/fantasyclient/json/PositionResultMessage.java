package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Building;
import com.example.fantasyclient.model.Monster;
import com.example.fantasyclient.model.Territory;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionResultMessage implements Serializable {
    private List<Territory> territoryArray;
    private List<Monster> monsterArray;
    private List<Building> buildingArray;

    public PositionResultMessage() {
    }

    public PositionResultMessage(List<Territory> territoryArray) {
        this.territoryArray = territoryArray;
    }

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

    public List<Building> getBuildingArray() {
        return buildingArray;
    }

    public void setBuildingArray(List<Building> buildingArray) {
        this.buildingArray = buildingArray;
    }
}