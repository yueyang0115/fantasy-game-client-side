package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Inventory;
import com.example.fantasyclient.model.WorldCoord;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ShopRequestMessage {
    private WorldCoord coord;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Inventory> selectedItems;//Map<inventoryID, amount to buy>
    private String action;//"list""buy""sell"

    public ShopRequestMessage() {
    }

    public ShopRequestMessage(WorldCoord coord, String action) {
        this.coord = coord;
        this.action = action;
    }

    public ShopRequestMessage(WorldCoord coord, List<Inventory> selectedItems, String action) {
        this.coord = coord;
        this.selectedItems = selectedItems;
        this.action = action;
    }

    public WorldCoord getCoord() {
        return coord;
    }

    public void setCoord(WorldCoord coord) {
        this.coord = coord;
    }

    public List<Inventory> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Inventory> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
