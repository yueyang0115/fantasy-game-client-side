package com.example.fantasyclient.model;

import java.util.Objects;

public abstract class Inventory {
    private int id;

    private DBItem item;

    private int amount;

    public Inventory() {
    }

    public Inventory(DBItem item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DBItem getDBItem() {
        return item;
    }

    public void setDBItem(DBItem item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public abstract int getOwnerID();
}
