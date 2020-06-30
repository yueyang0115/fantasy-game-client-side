package com.example.fantasyclient.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UnitInfoAdapterTest extends UnitAdapterTest{

    public UnitInfoAdapterTest() {
        super();
        testAdapter = new UnitInfoAdapter(testContext, testList);
    }
}