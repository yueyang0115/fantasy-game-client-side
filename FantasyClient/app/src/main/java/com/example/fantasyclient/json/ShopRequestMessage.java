package com.example.fantasyclient.json;

public class ShopRequestMessage {
    private int shopID;
    private int territoryID;
    private int itemID;
    private String action;//"list""buy""sell"

    public ShopRequestMessage() {
    }

    public ShopRequestMessage(int shopID, int territoryID, int itemID, String action) {
        this.shopID = shopID;
        this.territoryID = territoryID;
        this.itemID = itemID;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
