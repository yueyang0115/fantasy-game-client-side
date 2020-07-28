package com.example.fantasyclient.json;

import com.example.fantasyclient.model.PlayerInfo;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendResultMessage implements Serializable {
    List<PlayerInfo> playerInfoList;

    public FriendResultMessage() {
    }

    public FriendResultMessage(List<PlayerInfo> playerInfoList) {
        this.playerInfoList = playerInfoList;
    }

    public List<PlayerInfo> getPlayerInfoList() {
        return playerInfoList;
    }

    public void setPlayerInfoList(List<PlayerInfo> playerInfoList) {
        this.playerInfoList = playerInfoList;
    }
}
