package com.example.fantasyclient.json;

import java.io.Serializable;
import java.util.List;

public class BattleResultMessage implements Serializable {
    //initialize battle information when battle starts
    private BattleInitInfo battleInitInfo;
    private String result;
    private List<BattleAction> actions;

    public BattleResultMessage() {
    }

    public BattleResultMessage(BattleInitInfo battleInitInfo, String result, List<BattleAction> actions) {
        this.battleInitInfo = battleInitInfo;
        this.result = result;
        this.actions = actions;
    }

    public BattleInitInfo getBattleInitInfo() {
        return battleInitInfo;
    }

    public void setBattleInitInfo(BattleInitInfo battleInitInfo) {
        this.battleInitInfo = battleInitInfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BattleAction> getActions() {
        return actions;
    }

    public void setActions(List<BattleAction> actions) {
        this.actions = actions;
    }
}
