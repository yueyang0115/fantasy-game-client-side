package com.example.fantasyclient.adapter;

import com.example.fantasyclient.model.Building;

public class MapBuildingAdapterTest extends MapAdapterTest<Building> {

    public MapBuildingAdapterTest() {
        super();
        centerTarget = new Building("shop",100,centerCoord);
        testAdapter = new MapBuildingAdapter(testContext, centerCoord);
        CENTER = testAdapter.getCount()/2;
    }
}
