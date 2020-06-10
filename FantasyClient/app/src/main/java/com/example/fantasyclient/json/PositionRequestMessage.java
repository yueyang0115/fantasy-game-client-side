package com.example.fantasyclient.json;

import com.example.fantasyclient.model.WorldCoord;

import java.util.List;

public class PositionRequestMessage {
    private List<WorldCoord> coords;

    public PositionRequestMessage(){}

    public PositionRequestMessage(List<WorldCoord> coords) {
        this.coords = coords;
    }

    public List<WorldCoord> getCoords() {
        return coords;
    }

    public void setCoords(List<WorldCoord> coords) {
        this.coords = coords;
    }
}
