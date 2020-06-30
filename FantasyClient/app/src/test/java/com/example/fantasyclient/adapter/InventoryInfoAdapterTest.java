package com.example.fantasyclient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.model.DBItem;
import com.example.fantasyclient.model.Inventory;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class InventoryInfoAdapterTest {

    private Context testContext = ApplicationProvider.getApplicationContext();
    private List<Inventory> testList = new ArrayList<>(Arrays.asList(
            new Inventory(new DBItem("Test1",null),10),
            new Inventory(new DBItem("Test2","{Durability:10}"),5)));
    private InventoryInfoAdapter testAdapter = new InventoryInfoAdapter(testContext, testList);
    private BaseViewHolder testViewHolder;

    @Test
    public void getViewHolder() {
        testViewHolder = testAdapter.getViewHolder();
        assertNotNull(testViewHolder);
    }

    @Test
    public void setHighlightedPosition() {
        assertEquals(testAdapter.getHighlightedPosition(),0);
        testAdapter.setHighlightedPosition(1);
        assertEquals(testAdapter.getHighlightedPosition(),1);
    }

    @Test
    public void getDrawableByName() {
        Drawable drawable1 = testAdapter.getDrawableByName("castle");
        Drawable drawable2 = testAdapter.getDrawableByName("mine");
        Drawable drawable3 = testAdapter.getDrawableByName("INVALID");
        assertNotNull(drawable1);
        assertNotNull(drawable2);
        assertNull(drawable3);
        assertNotEquals(drawable1,drawable2);
    }

    @Test
    public void comprehensiveTest(){
        ListView listView = new ListView(testContext);
        listView.setAdapter(testAdapter);
        testList.add(new Inventory(new DBItem("Test",null),10));
        testAdapter.notifyDataSetChanged();
        ViewGroup testViewGroup = new ViewGroup(testContext) {
            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
            }
        };
        testAdapter.getView(0, null, testViewGroup);
        testAdapter.getView(1,null,testViewGroup);
    }
}