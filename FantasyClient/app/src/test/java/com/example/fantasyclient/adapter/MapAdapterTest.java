package com.example.fantasyclient.adapter;

import android.view.ViewGroup;
import android.widget.GridView;

import com.example.fantasyclient.model.WorldCoord;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public abstract class MapAdapterTest<T> extends HighlightedAdapterTest<T> {

    private static final int CENTER = 17;
    WorldCoord centerCoord;
    T centerObject;

    MapAdapterTest() {
        super();
        centerCoord = new WorldCoord(0,0);
    }

    @Test
    public void setHighlightedPosition() {
        assertEquals(testAdapter.getHighlightedPosition(),CENTER);
        testAdapter.setHighlightedPosition(1);
        assertEquals(testAdapter.getHighlightedPosition(),1);
    }

    @Test
    public void getItem(){
        T currObject = (T) testAdapter.getItem(CENTER);
        assertNull(currObject);
        ((MapAdapter)testAdapter).addToCacheByCoords(centerCoord, centerObject);
        currObject = (T) testAdapter.getItem(CENTER);
        assertNotNull(currObject);
        assertEquals(currObject,centerObject);
    }


    @Test
    public abstract void getImageDrawables();

    @Test
    public void comprehensiveTest(){
        GridView listView = new GridView(testContext);
        listView.setAdapter(testAdapter);
        ViewGroup testViewGroup = new ViewGroup(testContext) {
            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
            }
        };
        testAdapter.getView(CENTER, null, testViewGroup);
        testAdapter.getView(CENTER+1, null,testViewGroup);
        ((MapAdapter)testAdapter).addToCacheByCoords(centerCoord, centerObject);
        testAdapter.getView(CENTER, null,testViewGroup);
    }
}
