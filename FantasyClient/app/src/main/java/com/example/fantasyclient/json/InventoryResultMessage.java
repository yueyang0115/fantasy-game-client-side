package com.example.fantasyclient.json;

import com.example.fantasyclient.model.ItemPack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InventoryResultMessage implements Serializable {

    private List<ItemPack> items = new ArrayList<>(); //all items of player
    private String result; //status: "valid","invalid"
    private int money;

    public List<ItemPack> getItems() {
        return items;
    }

    public void setItems(List<ItemPack> items) {
        this.items = items;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public InventoryResultMessage() {
        super();
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
