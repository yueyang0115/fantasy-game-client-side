package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Inventory;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopResultMessage implements Serializable {

    private List<Inventory> items = new ArrayList<>(); //all items in the territory
    private String result; //status: "valid","invalid"

    public ShopResultMessage() {
    }

    public ShopResultMessage(String result) {
        this.result = result;
    }

    public ShopResultMessage(List<Inventory> items, String result) {
        this.items = items;
        this.result = result;
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
