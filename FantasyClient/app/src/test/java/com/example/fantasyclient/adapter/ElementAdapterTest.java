package com.example.fantasyclient.adapter;

import android.view.ViewGroup;
import android.widget.ListView;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class ElementAdapterTest<T> extends HighlightedAdapterTest<T> {

    List<T> testList;

    ElementAdapterTest() {
        super();
    }

    @Test
    public abstract void getViewHolder();

    @Test
    public void setHighlightedPosition() {
        assertEquals(testAdapter.getHighlightedPosition(),0);
        testAdapter.setHighlightedPosition(1);
        assertEquals(testAdapter.getHighlightedPosition(),1);
    }

    @Test
    public void comprehensiveTest(){
        ListView listView = new ListView(testContext);
        listView.setAdapter(testAdapter);
        ViewGroup testViewGroup = new ViewGroup(testContext) {
            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
            }
        };
        testAdapter.getView(0, null, testViewGroup);
        testAdapter.getView(1, null,testViewGroup);
    }
}
