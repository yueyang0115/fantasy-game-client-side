package com.example.fantasyclient.json;

public class PositionUpdateMessage extends JsonBase{
    private double x;
    private double y;

    public PositionUpdateMessage(String type, double x, double y) {
        super();
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
