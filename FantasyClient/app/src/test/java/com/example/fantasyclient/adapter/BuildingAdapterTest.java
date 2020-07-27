package com.example.fantasyclient.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.model.Building;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public abstract class BuildingAdapterTest extends ElementAdapterTest<Building> {

    BuildingAdapterTest() {
        super();
        testList = new ArrayList<>(Arrays.asList(
                new Building("Test1",10),
                new Building("Test2",20)));
    }

    @Test
    public void getViewHolder() {
        BaseViewHolder testViewHolder = ((BuildingAdapter)testAdapter).getViewHolder();
        assertNotNull(testViewHolder);
    }
}