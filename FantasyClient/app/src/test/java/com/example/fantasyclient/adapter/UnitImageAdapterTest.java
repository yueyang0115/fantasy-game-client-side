package com.example.fantasyclient.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UnitImageAdapterTest extends UnitAdapterTest{

    public UnitImageAdapterTest() {
        super();
        testAdapter = new UnitImageAdapter(testContext, testList);
    }
}