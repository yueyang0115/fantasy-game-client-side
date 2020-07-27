package com.example.fantasyclient.model;

public class UnitEquipment extends Inventory {
    private Unit unit;

    public UnitEquipment() {
    }

    public UnitEquipment(DBItem item, int amount, Unit unit) {
        super(item, amount);
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}
