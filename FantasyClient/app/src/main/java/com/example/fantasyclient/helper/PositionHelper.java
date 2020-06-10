package com.example.fantasyclient.helper;

import com.example.fantasyclient.model.VirtualPosition;

public class PositionHelper {
    private static int width_unit = 10;
    private static int height_unit = 10;

    /**
     * This function takes a lattitude/longitude and converts it to world coordinate (VirtualPosition).
     * Note that world coordinates go from -1,800,000 to 1,800,000 in both directions
     * @param answer is an out parameter filled in with the result.
     * @param latitude the latitude in degrees
     * @param longitude the longitude in degrees
     */
    public static void convertVPosition(VirtualPosition answer, double latitude, double longitude){
        //experimentally, xx.xxx8 -> xx.xxx9 (i.e. +0.0001) of lat or long is a good amount to be one square in the world
        //this was derived by looking at lat/longs moving along a street and making about 1-2 squares per where a house is.
        //this experiment was done in NC on the map, but confirmed to be reasonable in northern Canda and the tip of South America.

        answer.setX((int)(longitude*10000));
        answer.setY((int)(latitude*10000));

    }
    public static void oldConvertVPosition(VirtualPosition answer, double latitude, double longitude) {
        //we get latitude and longitude in degrees.  However, we need them in radians to do trig functions
        double longitude_radians = (longitude * Math.PI) / 180;
        double lattitude_radians = (latitude * Math.PI) / 180;
        //next we use the Miller Cylindrical Projection to "flatten" the lat/long into a "rectangle"
        double y = (1.25 * Math.log(Math.tan(0.25 * Math.PI + 0.4 * lattitude_radians)));
        double x = longitude_radians;
        //next we want to convert these into "meters".  These are really only meters at the equator.
        double EARTH_CIRCUMFERENCE_METERS = 6381372 * Math.PI * 2;
        double W = EARTH_CIRCUMFERENCE_METERS;
        double H = EARTH_CIRCUMFERENCE_METERS / 2;
        double mill = 2.3;// miller projection parameter

    //now Im totally lost.
        y = ((H / 2) - (H / (2 * mill))) * y;
        x = ((W / 2) + (W / (2 * Math.PI))) * x;
        int tempX = (int) x;
        int tempY = (int) y;

        int center_x = (int) ((tempX > 0) ? (tempX / width_unit) * width_unit + width_unit / 2 : (tempX / width_unit) * width_unit - width_unit / 2);
        int center_y = (int) ((tempY > 0) ? (tempY / height_unit) * height_unit + height_unit / 2 : (tempY / height_unit) * height_unit - height_unit / 2);

        answer.setX(center_x);
        answer.setY(center_y);
    }

}
