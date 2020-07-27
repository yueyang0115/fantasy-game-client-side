package com.example.fantasyclient.helper;

public class MapMoveTool {

    private float startX;
    private float startY;

    private float destX;
    private float destY;

    private int divideFactor = 200;
    //Max allowed distance to move during a "click", in DP.
    private static final int MAX_CLICK_DISTANCE = 15;

    public void setAmplificationFactor(int amplificationFactor) {
        divideFactor = 200-30*amplificationFactor;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public void setDestX(float destX) {
        this.destX = destX;
    }

    public void setDestY(float destY) {
        this.destY = destY;
    }

    public int getOffsetX(){
        return (int) ((destX-startX)/divideFactor);
    }

    public int getOffsetY(){
        return (int) ((destY-startY)/divideFactor);
    }

    public boolean ifStayedWithinClickDistance(float density){
        return distanceInDp(density) < MAX_CLICK_DISTANCE;
    }

    /**
     * This method calculates distance of drag
     * @param density the factor to convert pixel to dp, corresponding to target device
     */
    private float distanceInDp(float density) {
        float dx = startX - destX;
        float dy = startY - destY;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return distanceInPx / density;
    }
}
