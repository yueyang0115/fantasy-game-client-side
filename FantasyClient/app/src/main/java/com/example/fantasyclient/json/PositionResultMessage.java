package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Territory;

import java.util.List;

public class PositionResultMessage {
    private List<Territory> territoryArray;

    public List<Territory> getTerritoryArray() {
        return territoryArray;
    }

    public void setTerritoryArray(List<Territory> territoryArray) {
        this.territoryArray = territoryArray;
    }
}