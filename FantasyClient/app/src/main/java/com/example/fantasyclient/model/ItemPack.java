package com.example.fantasyclient.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemPack {
    private int id;


    @JsonBackReference
    private Shop shop;

//    @JsonManagedReference
    private Item item;

    private int amount;

    public ItemPack(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public ItemPack() {
    }


    public Shop getShop() {
        return shop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int number) {
        this.amount = number;
    }
}
