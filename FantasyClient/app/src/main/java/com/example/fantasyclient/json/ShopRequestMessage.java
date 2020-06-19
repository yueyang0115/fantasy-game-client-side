package com.example.fantasyclient.json;

import com.example.fantasyclient.model.WorldCoord;

import java.util.Map;

public class ShopRequestMessage {
    private WorldCoord coord;
    private Map<Integer,Integer> itemMap;//Map<inventoryID, amount to buy>
    private String action;//"list""buy""sell"

    public ShopRequestMessage() {
    }

    public ShopRequestMessage(WorldCoord coord, String action) {
        this.coord = coord;
        this.action = action;
    }

    public ShopRequestMessage(WorldCoord coord, Map<Integer,Integer> itemMap, String action) {
        this.coord = coord;
        this.itemMap = itemMap;
        this.action = action;
    }

    public WorldCoord getCoord() {
        return coord;
    }

    public void setCoord(WorldCoord coord) {
        this.coord = coord;
    }

    public Map<Integer, Integer> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Integer, Integer> itemMap) {
        this.itemMap = itemMap;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
