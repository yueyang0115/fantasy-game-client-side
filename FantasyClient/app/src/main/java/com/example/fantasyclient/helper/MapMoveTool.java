package com.example.fantasyclient.helper;

public class MapMoveTool {

    private int startX;
    private int startY;

    private int destX;
    private int destY;

    private int divideFactor = 200;

    public void setAmplificationFactor(int amplificationFactor) {
        divideFactor = 200-30*amplificationFactor;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setDestX(int destX) {
        this.destX = destX;
    }

    public void setDestY(int destY) {
        this.destY = destY;
    }

    public int getOffsetX(){
        return (destX-startX)/divideFactor;
    }

    public int getOffsetY(){
        return (destY-startY)/divideFactor;
    }
}
