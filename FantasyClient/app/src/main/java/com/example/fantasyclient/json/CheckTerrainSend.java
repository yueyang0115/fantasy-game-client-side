package com.example.fantasyclient.json;

public class CheckTerrainSend extends JsonBase{
    private Terrain terrain;

    public CheckTerrainSend(String type, String terrainType, int terrainID) {
        super();
        this.type = type;
        terrain = new Terrain(terrainID, terrainType);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
}
