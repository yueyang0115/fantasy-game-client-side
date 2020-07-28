package com.example.fantasyclient.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendRequestMessage {
    public enum ActionType {
        search, apply;
    }

    String username;
    ActionType action;
    int id;

    public FriendRequestMessage() {
    }

    public FriendRequestMessage(ActionType action, int id) {
        this.action = action;
        this.id = id;
    }

    public FriendRequestMessage(String username, ActionType action) {
        this.username = username;
        this.action = action;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
