package com.example.fantasyclient.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BuildingInfoAdapterTest extends BuildingAdapterTest{

    public BuildingInfoAdapterTest() {
        super();
        testAdapter = new BuildingInfoAdapter(testContext, testList);
    }
}