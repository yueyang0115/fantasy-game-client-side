package com.example.fantasyclient.json;

public class BattleRequestMessage {

    private int territoryID;
    private String action;//"attack","escape","start"
    private BattleAction battleAction; //include attackerID, attackeeID, action("normal, magical")

    public BattleRequestMessage() {
    }

    //constructor for "start"
    public BattleRequestMessage(int territoryID, String action) {
        this.territoryID = territoryID;
        this.action = action;
    }

    //constructor for "escape"
    public BattleRequestMessage(String action) {
        this.action = action;
    }

    //constructor for "attack"
    public BattleRequestMessage(int territoryID, String action, BattleAction battleAction) {
        this.territoryID = territoryID;
        this.action = action;
        this.battleAction = battleAction;
    }

    public int getTerritoryID() {
        return territoryID;
    }

    public void setTerritoryID(int territoryID) {
        this.territoryID = territoryID;
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
