package com.example.fantasyclient.helper;

import com.example.fantasyclient.model.VirtualPosition;

public class PositionHelper {

    public static void convertVPosition(VirtualPosition vPosition, double latitude, double longitude) {
        double L = 6381372 * Math.PI * 2;// perimeter of earth
        double W = L;
        double H = L / 2;
        double mill = 2.3;// miller projection parameter
        double x = (longitude * Math.PI) / 180;
        double y = (latitude * Math.PI) / 180;
        y = (1.25 * Math.log(Math.tan(0.25 * Math.PI + 0.4 * y)));
        x = ((W / 2) + (W / (2 * Math.PI))) * x;
        y = ((H / 2) - (H / (2 * mill))) * y;
        vPosition.setX((int)x);
        vPosition.setY((int)y);
    }

}
