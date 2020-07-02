package com.example.fantasyclient.adapter;

import android.view.ViewGroup;
import android.widget.GridView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.model.WorldCoord;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public abstract class MapAdapterTest<T> extends HighlightedAdapterTest<T> {

    int CENTER;
    WorldCoord centerCoord;
    T centerTarget;

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
    public void imageMapCacheTest(){
        MapAdapter<T> mapAdapter = (MapAdapter<T>) testAdapter;
        //image map is initialized to be empty
        cacheTestNullCheck(mapAdapter,centerCoord,centerTarget,CENTER);
        //add centerTarget to map
        mapAdapter.addToCacheByCoords(centerCoord, centerTarget);
        cacheTestNotNullCheck(mapAdapter,centerCoord,centerTarget,CENTER);
        //remove centerTarget by coords
        mapAdapter.removeFromCacheByCoords(centerCoord);
        cacheTestNullCheck(mapAdapter,centerCoord,centerTarget,CENTER);
        //add back to map
        mapAdapter.addToCacheByCoords(centerCoord,centerTarget);
        cacheTestNotNullCheck(mapAdapter,centerCoord,centerTarget,CENTER);
        //remove centerTarget by target
        mapAdapter.removeFromCacheByTarget(centerTarget);
        cacheTestNullCheck(mapAdapter,centerCoord,centerTarget,CENTER);
    }

    private void cacheTestNullCheck(MapAdapter<T> mapAdapter, WorldCoord coord, T target, int position){
        assertFalse(mapAdapter.checkCacheByCoords(coord));
        assertFalse(mapAdapter.checkCacheByTarget(target));
        assertNull(mapAdapter.getItem(position));
        assertNull(mapAdapter.getCachedTargetByCoord(coord));
        assertNull(mapAdapter.getCachedCoordByTarget(target));
    }

    private void cacheTestNotNullCheck(MapAdapter<T> mapAdapter, WorldCoord coord, T target, int position){
        assertTrue(mapAdapter.checkCacheByCoords(coord));
        assertTrue(mapAdapter.checkCacheByTarget(target));
        assertEquals(mapAdapter.getItem(position), target);
        assertEquals(mapAdapter.getCachedCoordByTarget(target),coord);
        assertEquals(mapAdapter.getCachedTargetByCoord(coord),target);
    }

    @Test
    public void initImageTest(){
        MapAdapter<T> mapAdapter = (MapAdapter<T>) testAdapter;
        mapAdapter.initImage(R.drawable.desert);
    }

    @Test
    public void queriedCoordTest(){
        MapAdapter<T> mapAdapter = (MapAdapter<T>) testAdapter;
        mapAdapter.updateCurrCoord(centerCoord);
        List<WorldCoord> queriedCoords = mapAdapter.getQueriedCoords();
        assertEquals(queriedCoords.size(), testAdapter.getCount());
    }

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
        ((MapAdapter)testAdapter).addToCacheByCoords(centerCoord, centerTarget);
        testAdapter.getView(CENTER, null,testViewGroup);
    }
}
