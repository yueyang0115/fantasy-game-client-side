package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ShopResultMessage {
    private List<Item> items = new ArrayList<>(); //all items in the territory
    private String result; //status: "valid","invalid"

    public ShopResultMessage() {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
