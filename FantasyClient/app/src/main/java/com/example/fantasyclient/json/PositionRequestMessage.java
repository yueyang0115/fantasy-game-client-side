package com.example.fantasyclient.json;

import com.example.fantasyclient.model.WorldCoord;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionRequestMessage {
    private List<WorldCoord> coords;
    private WorldCoord currentCoord;

    public PositionRequestMessage(){}

    public PositionRequestMessage(List<WorldCoord> coords, WorldCoord coord) {
        this.coords = coords;
        this.currentCoord = coord;
    }

    public List<WorldCoord> getCoords() {
        return coords;
    }

    public void setCoords(List<WorldCoord> coords) {
        this.coords = coords;
    }

    public WorldCoord getCurrentCoord() {
        return currentCoord;
    }

    public void setCurrentCoord(WorldCoord currentCoord) {
        this.currentCoord = currentCoord;
    }
}
