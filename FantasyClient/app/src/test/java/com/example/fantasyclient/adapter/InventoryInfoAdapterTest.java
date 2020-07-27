package com.example.fantasyclient.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class InventoryInfoAdapterTest extends InventoryAdapterTest{

    public InventoryInfoAdapterTest() {
        super();
        testAdapter = new InventoryInfoAdapter(testContext, testList);
    }
}