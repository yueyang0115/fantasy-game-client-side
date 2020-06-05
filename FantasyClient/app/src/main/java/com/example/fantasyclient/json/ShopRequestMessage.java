package com.example.fantasyclient.json;

public class ShopRequestMessage {
    private int shopID;
    private int territoryID;
    private int itemID;
    private String action;//"list""buy""sell"

    public ShopRequestMessage() {
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
