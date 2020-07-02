package com.example.fantasyclient.adapter;

import com.example.fantasyclient.model.Territory;

public class MapTerritoryAdapterTest extends MapAdapterTest<Territory> {

    public MapTerritoryAdapterTest() {
        super();
        centerTarget = new Territory(centerCoord, 0, "grass");
        testAdapter = new MapTerritoryAdapter(testContext, centerCoord);
        CENTER = testAdapter.getCount()/2;
    }
}
