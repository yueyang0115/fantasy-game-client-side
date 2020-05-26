package com.example.fantasyclient.json;

public class CheckTerrainSend {
    private Terrain terrain;

    public CheckTerrainSend(String terrainType, int terrainID) {
        terrain = new Terrain(terrainID, terrainType);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
}
