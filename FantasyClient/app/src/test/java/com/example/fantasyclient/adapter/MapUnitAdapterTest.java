package com.example.fantasyclient.adapter;

import com.example.fantasyclient.model.Monster;
import com.example.fantasyclient.model.Unit;

public class MapUnitAdapterTest extends MapAdapterTest<Unit> {

    public MapUnitAdapterTest() {
        super();
        centerTarget = new Monster("monster","wolf",100,10,20,centerCoord,false);
        testAdapter = new MapUnitAdapter(testContext, centerCoord);
        CENTER = testAdapter.getCount()/2;
    }
}
