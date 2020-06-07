package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Item;
import com.example.fantasyclient.model.ItemPack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopResultMessage implements Serializable {
    private List<ItemPack> items = new ArrayList<>(); //all items in the territory
    private String result; //status: "valid","invalid"

    public ShopResultMessage() {
    }

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
}
