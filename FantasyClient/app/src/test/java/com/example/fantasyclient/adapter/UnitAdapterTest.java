package com.example.fantasyclient.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.model.Unit;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public abstract class UnitAdapterTest extends ElementAdapterTest<Unit> {

    UnitAdapterTest() {
        super();
        testList = new ArrayList<>(Arrays.asList(
                new Unit("soldier", "test1", 20, 10, 10),
                new Unit("monster", "test2", 30, 5, 5)));
    }

    @Test
    public void getViewHolder() {
        BaseViewHolder testViewHolder = ((UnitAdapter)testAdapter).getViewHolder();
        assertNotNull(testViewHolder);
    }
}