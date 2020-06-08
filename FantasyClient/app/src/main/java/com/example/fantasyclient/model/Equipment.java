package com.example.fantasyclient.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Equipment extends Item {

    @JsonBackReference(value = "unit-equipment")
    private Unit unit;

}
