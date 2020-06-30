package com.example.fantasyclient.adapter;

import com.example.fantasyclient.model.Building;

public class MapBuildingAdapterTest extends MapAdapterTest<Building> {

    public MapBuildingAdapterTest() {
        super();
        centerObject = new Building("shop",100,centerCoord);
        testAdapter = new MapBuildingAdapter(testContext, centerCoord);
    }

    @Override
    public void getImageDrawables() {

    }
}
