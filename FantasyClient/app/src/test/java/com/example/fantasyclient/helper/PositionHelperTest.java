package com.example.fantasyclient.helper;

import com.example.fantasyclient.model.WorldCoord;

import org.junit.Test;

import static org.junit.Assert.*;

public class PositionHelperTest {

    @Test
    public void convertVPosition() {
        WorldCoord coord = new WorldCoord(0,0);
        PositionHelper.convertVPosition(coord,37,-20);
        assertNotNull(coord);
        assertEquals(coord.getX(),-200000);
        assertEquals(coord.getY(),370000);
    }
}