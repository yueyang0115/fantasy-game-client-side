package com.example.fantasyclient.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendRequestMessage {
    public enum ActionType {
        search, apply;
    }

    String userName;
    ActionType action;
    int id;

    public FriendRequestMessage() {
    }

    public FriendRequestMessage(ActionType action, int id) {
        this.action = action;
        this.id = id;
    }

    public FriendRequestMessage(String userName, ActionType action) {
        this.userName = userName;
        this.action = action;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
