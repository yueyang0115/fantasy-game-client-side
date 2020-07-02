package com.example.fantasyclient.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.model.Territory;
import com.example.fantasyclient.model.WorldCoord;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public abstract class TerritoryAdapterTest extends ElementAdapterTest<Territory> {

    TerritoryAdapterTest() {
        super();
        testList = new ArrayList<>(Arrays.asList(
                new Territory(new WorldCoord(0,0),0,"grass"),
                new Territory(new WorldCoord(0,1),10,"river")));
    }

    @Test
    public void getViewHolder() {
        BaseViewHolder testViewHolder = ((TerritoryAdapter)testAdapter).getViewHolder();
        assertNotNull(testViewHolder);
    }
}