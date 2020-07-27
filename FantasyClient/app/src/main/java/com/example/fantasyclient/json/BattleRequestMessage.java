package com.example.fantasyclient.json;

import com.example.fantasyclient.model.BattleAction;
import com.example.fantasyclient.model.WorldCoord;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BattleRequestMessage {

    private WorldCoord territoryCoord;
    private String action;//"attack","escape","start"
    private BattleAction battleAction; //include attackerID, attackeeID, action("normal, magical")

    public BattleRequestMessage() {
    }

    //constructor for "start"
    public BattleRequestMessage(WorldCoord territoryCoord, String action) {
        this.territoryCoord = territoryCoord;
        this.action = action;
    }

    //constructor for "escape"
    public BattleRequestMessage(String action) {
        this.action = action;
    }

    //constructor for "attack"
    public BattleRequestMessage(WorldCoord territoryCoord, String action, BattleAction battleAction) {
        this.territoryCoord = territoryCoord;
        this.action = action;
        this.battleAction = battleAction;
    }

    public WorldCoord getTerritoryCoord() {
        return territoryCoord;
    }

    public void setTerritoryCoord(WorldCoord territoryCoord) {
        this.territoryCoord = territoryCoord;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public BattleAction getBattleAction() { return battleAction; }

    public void setBattleAction(BattleAction battleAction) { this.battleAction = battleAction; }
}
