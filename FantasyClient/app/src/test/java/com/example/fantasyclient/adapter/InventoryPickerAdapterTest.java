package com.example.fantasyclient.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.fantasyclient.model.Inventory;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

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
        List<Inventory> selectedItems = ((InventoryPickerAdapter)testAdapter).getSelectedItems();
        assertNotNull(selectedItems);
        assertTrue(selectedItems.isEmpty());
        ((InventoryPickerAdapter)testAdapter).clearMap();
        assertTrue(selectedItems.isEmpty());
    }

}