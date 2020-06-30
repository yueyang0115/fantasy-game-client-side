package com.example.fantasyclient.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class InventoryPickerAdapterTest extends InventoryAdapterTest{

    public InventoryPickerAdapterTest() {
        super();
        testAdapter = new InventoryPickerAdapter(testContext, testList);
    }

    @Test
    public void mapTest(){
        Map<Integer, Integer> map = ((InventoryPickerAdapter)testAdapter).getItemMap();
        assertNotNull(map);
        assertTrue(map.isEmpty());
        ((InventoryPickerAdapter)testAdapter).clearMap();
        assertTrue(map.isEmpty());
    }

}