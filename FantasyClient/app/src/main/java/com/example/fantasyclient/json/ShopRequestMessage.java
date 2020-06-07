package com.example.fantasyclient.json;

import java.util.Map;

public class ShopRequestMessage {
    private int shopID;
    private int territoryID;
    private Map<Integer,Integer> itemMap;//Map<itemPackID, amount to buy>
    private String action;//"list""buy""sell"

    public ShopRequestMessage() {
    }

    public ShopRequestMessage(int shopID, int territoryID, Map<Integer,Integer> itemMap, String action) {
        this.shopID = shopID;
        this.territoryID = territoryID;
        this.itemMap = itemMap;
        this.action = action;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public int getTerritoryID() {
        return territoryID;
    }

    public void setTerritoryID(int territoryID) {
        this.territoryID = territoryID;
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
