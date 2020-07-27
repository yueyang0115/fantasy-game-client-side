package com.example.fantasyclient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public abstract class HighlightedAdapterTest<T> {

    Context testContext;
    HighlightAdapter testAdapter;

    HighlightedAdapterTest() {
        testContext = ApplicationProvider.getApplicationContext();
    }

    @Test
    public abstract void setHighlightedPosition();

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
    public abstract void comprehensiveTest();
}