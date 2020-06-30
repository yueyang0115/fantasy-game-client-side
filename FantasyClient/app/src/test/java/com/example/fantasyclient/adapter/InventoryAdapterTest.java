package com.example.fantasyclient.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.model.DBItem;
import com.example.fantasyclient.model.Inventory;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public abstract class InventoryAdapterTest extends ElementAdapterTest<Inventory> {

    InventoryAdapterTest() {
        super();
        testList = new ArrayList<>(Arrays.asList(
                new Inventory(new DBItem("Test1",null),10),
                new Inventory(new DBItem("Test2","{name:test2,cost:10}"),5)));
    }

    @Test
    public void getViewHolder() {
        BaseViewHolder testViewHolder = ((InventoryAdapter)testAdapter).getViewHolder();
        assertNotNull(testViewHolder);
    }
}