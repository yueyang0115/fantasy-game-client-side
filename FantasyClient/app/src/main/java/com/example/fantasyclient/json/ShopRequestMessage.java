package com.example.fantasyclient.json;

import java.util.Map;

public class ShopRequestMessage {
    private int shopID;
    private Map<Integer,Integer> itemMap;//Map<inventoryID, amount to buy>
    private String action;//"list""buy""sell"

    public ShopRequestMessage() {
    }

    public ShopRequestMessage(int shopID, String action) {
        this.shopID = shopID;
        this.action = action;
    }

    public ShopRequestMessage(int shopID, Map<Integer,Integer> itemMap, String action) {
        this.shopID = shopID;
        this.itemMap = itemMap;
        this.action = action;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
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
