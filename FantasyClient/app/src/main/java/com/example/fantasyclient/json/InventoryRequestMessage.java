package com.example.fantasyclient.json;

public class InventoryRequestMessage {

    private String action;//"list" "use" "drop"
    private int inventoryID;
    private int unitID;

    public InventoryRequestMessage() {
    }

    public InventoryRequestMessage(String action) {
        this.action = action;
    }

    public InventoryRequestMessage(String action, int inventoryID, int unitID) {
        this.action = action;
        this.inventoryID = inventoryID;
        this.unitID = unitID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }
}
