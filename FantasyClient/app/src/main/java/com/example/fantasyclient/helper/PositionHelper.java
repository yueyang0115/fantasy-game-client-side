package com.example.fantasyclient.helper;

import com.example.fantasyclient.model.VirtualPosition;
import com.example.fantasyclient.model.WorldCoord;

public class PositionHelper {
    private static int width_unit = 10;
    private static int height_unit = 10;

    /**
     * This function takes a latitude/longitude and converts it to world coordinate (VirtualPosition).
     * Note that world coordinates go from -1,800,000 to 1,800,000 in both directions
     * @param answer is an out parameter filled in with the result.
     * @param latitude the latitude in degrees
     * @param longitude the longitude in degrees
     */
    public static void convertVPosition(WorldCoord answer, double latitude, double longitude){
        //experimentally, xx.xxx8 -> xx.xxx9 (i.e. +0.0001) of lat or long is a good amount to be one square in the world
        //this was derived by looking at lat/longs moving along a street and making about 1-2 squares per where a house is.
        //this experiment was done in NC on the map, but confirmed to be reasonable in northern Canda and the tip of South America.
        answer.setX((int)(longitude*10000));
        answer.setY((int)(latitude*10000));
    }

}
