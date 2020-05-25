package com.example.fantasyclient.json;

public class MessagesC2S {
    private LoginInfoMessage loginMessage;
    private PositionUpdateMessage positionMessage;

    public MessagesC2S(LoginInfoMessage loginMessage) {
        this.loginMessage = loginMessage;
    }

    public MessagesC2S(PositionUpdateMessage positionMessage) {
        this.positionMessage = positionMessage;
    }

    public LoginInfoMessage getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(LoginInfoMessage loginMessage) {
        this.loginMessage = loginMessage;
    }

    public PositionUpdateMessage getPositionMessage() {
        return positionMessage;
    }

    public void setPositionMessage(PositionUpdateMessage positionMessage) {
        this.positionMessage = positionMessage;
    }
}
