package com.example.fantasyclient.json;

public class InventoryResultMessage extends ItemResultMessage {

    private int money;

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
