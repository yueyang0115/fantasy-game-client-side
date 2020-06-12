package com.example.fantasyclient.json;

import com.example.fantasyclient.model.Item;
import com.example.fantasyclient.model.ItemPack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InventoryResultMessage implements Serializable {

    private AttributeResultMessage attributeResultMessage;
    private List<Item> items = new ArrayList<>(); //all items of player
    private String result; //status: "valid","invalid"
    private int money;

    public InventoryResultMessage() {
        super();
    }

    public AttributeResultMessage getAttributeResultMessage() {
        return attributeResultMessage;
    }

    public void setAttributeResultMessage(AttributeResultMessage attributeResultMessage) {
        this.attributeResultMessage = attributeResultMessage;
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


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
