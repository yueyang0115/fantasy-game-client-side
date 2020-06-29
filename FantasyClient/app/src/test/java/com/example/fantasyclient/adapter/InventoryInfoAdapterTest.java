package com.example.fantasyclient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ListView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.model.Inventory;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class InventoryInfoAdapterTest {

    private Context testContext = ApplicationProvider.getApplicationContext();
    private List<Inventory> testList = new ArrayList<>();
    private int layout = R.layout.item_layout;
    private InventoryInfoAdapter testAdapter = new InventoryInfoAdapter(testContext, testList);
    private BaseViewHolder testViewHolder;

    @Test
    public void getViewHolder() {
        testViewHolder = testAdapter.getViewHolder();
        assertNotNull(testViewHolder);
    }

    @Test
    public void findView() {
        ListView listView = new ListView(testContext);
        listView.setAdapter(testAdapter);

        /*int measureSpec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY);

        testAdapter.notifyDataSetChanged();
        listView.measure(measureSpec, measureSpec);
        listView.layout(0, 0, 100, 100);*/
    }

    @Test
    public void getView() {
    }

    @Test
    public void setHighlightedPosition() {
        testAdapter.setHighlightedPosition(10);
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
    public void setImageByPosition() {
    }
}