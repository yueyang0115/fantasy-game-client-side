package com.example.fantasyclient.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

public class Shop extends Building {

//    @JsonManagedReference
    private List<Item> inventory = new ArrayList<>();

    public Shop() {
    }

    public Shop(String name) {
        super(name);
    }

    public Shop addInventory(Item item) {
        inventory.add(item);
        item.addShop(this);
        return this;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }
}
