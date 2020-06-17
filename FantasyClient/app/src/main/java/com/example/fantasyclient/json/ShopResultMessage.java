package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopResultMessage implements Serializable {

    private InventoryResultMessage inventoryResultMessage;
    private List<Inventory> items = new ArrayList<>(); //all items in the territory
    private String result; //status: "valid","invalid"

    public ShopResultMessage() {
    }

    public InventoryResultMessage getInventoryResultMessage() {
        return inventoryResultMessage;
    }

    public void setInventoryResultMessage(InventoryResultMessage inventoryResultMessage) {
        this.inventoryResultMessage = inventoryResultMessage;
    }

    public List<Inventory> getItems() {
        return items;
    }

    public void setItems(List<Inventory> items) {
        this.items = items;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
