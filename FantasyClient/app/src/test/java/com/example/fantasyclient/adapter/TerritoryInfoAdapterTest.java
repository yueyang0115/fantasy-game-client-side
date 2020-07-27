package com.example.fantasyclient.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TerritoryInfoAdapterTest extends TerritoryAdapterTest{

    public TerritoryInfoAdapterTest() {
        super();
        testAdapter = new TerritoryInfoAdapter(testContext, testList);
    }
}