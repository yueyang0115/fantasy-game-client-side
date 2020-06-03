package com.example.fantasyclient.json;

public class MessagesS2C {
    private PositionResultMessage positionResultMessage;
    private LoginResultMessage loginResultMessage;
    private SignUpResultMessage signUpResultMessage;
    private BattleResultMessage battleResultMessage;
    private AttributeResultMessage attributeResultMessage;

    public MessagesS2C(){
    }

    public PositionResultMessage getPositionResultMessage() {
        return positionResultMessage;
    }

    public void setPositionResultMessage(PositionResultMessage positionResultMessage) {
        this.positionResultMessage = positionResultMessage;
    }

    public LoginResultMessage getLoginResultMessage() {
        return loginResultMessage;
    }

    public void setLoginResultMessage(LoginResultMessage loginResultMessage) {
        this.loginResultMessage = loginResultMessage;
    }

    public SignUpResultMessage getSignUpResultMessage() {
        return signUpResultMessage;
    }

    public void setSignUpResultMessage(SignUpResultMessage signUpResultMessage) {
        this.signUpResultMessage = signUpResultMessage;
    }

    public BattleResultMessage getBattleResultMessage() {
        return battleResultMessage;
    }

    public void setBattleResultMessage(BattleResultMessage battleResultMessage) {
        this.battleResultMessage = battleResultMessage;
    }

    public AttributeResultMessage getAttributeResultMessage() {
        return attributeResultMessage;
    }

    public void setAttributeResultMessage(AttributeResultMessage attributeResultMessage) {
        this.attributeResultMessage = attributeResultMessage;
    }
}
