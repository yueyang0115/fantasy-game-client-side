package com.example.fantasyclient.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {

    private int id;

    private String name;

    private int cost;

    @JsonBackReference
    private List<Shop> shop_list = new ArrayList<>();

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Shop> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<Shop> shop_list) {
        this.shop_list = shop_list;
    }

    public void addShop(Shop shop){
        shop_list.add(shop);
    }

    public Item(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }
}
