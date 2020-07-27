package com.example.fantasyclient.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendRequestMessage {
    String userName;
    String action;//"apply" "search"

    public FriendRequestMessage() {
    }

    public FriendRequestMessage(String userName, String action) {
        this.userName = userName;
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
