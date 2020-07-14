package com.example.fantasyclient.json;

import java.io.Serializable;

public class MessagesS2C implements Serializable {
    private PositionResultMessage positionResultMessage;
    private LoginResultMessage loginResultMessage;
    private SignUpResultMessage signUpResultMessage;
    private BattleResultMessage battleResultMessage;
    private AttributeResultMessage attributeResultMessage;
    private ShopResultMessage shopResultMessage;
    private InventoryResultMessage inventoryResultMessage;
    private BuildingResultMessage buildingResultMessage;
    private RedirectMessage redirectMessage;
    private LevelUpResultMessage levelUpResultMessage;

    //constructors

    public MessagesS2C(){
    }

    public MessagesS2C(PositionResultMessage positionResultMessage){
        this.positionResultMessage = positionResultMessage;
    }

    public MessagesS2C(LoginResultMessage loginResultMessage){
        this.loginResultMessage = loginResultMessage;
    }

    public MessagesS2C(SignUpResultMessage signUpResultMessage){
        this.signUpResultMessage = signUpResultMessage;
    }

    public MessagesS2C(BattleResultMessage battleResultMessage){
        this.battleResultMessage = battleResultMessage;
    }

    public MessagesS2C(AttributeResultMessage attributeResultMessage){
        this.attributeResultMessage = attributeResultMessage;
    }

    public MessagesS2C(ShopResultMessage shopResultMessage){
        this.shopResultMessage = shopResultMessage;
    }

    public MessagesS2C(InventoryResultMessage inventoryResultMessage){
        this.inventoryResultMessage = inventoryResultMessage;
    }

    public MessagesS2C(BuildingResultMessage buildingResultMessage){
        this.buildingResultMessage = buildingResultMessage;
    }

    public MessagesS2C(RedirectMessage redirectMessage) {
        this.redirectMessage = redirectMessage;
    }

    public MessagesS2C(LevelUpResultMessage levelUpResultMessage) {
        this.levelUpResultMessage = levelUpResultMessage;
    }

    //Getters and setters

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

    public ShopResultMessage getShopResultMessage() {
        return shopResultMessage;
    }

    public void setShopResultMessage(ShopResultMessage shopResultMessage) {
        this.shopResultMessage = shopResultMessage;
    }

    public InventoryResultMessage getInventoryResultMessage() {
        return inventoryResultMessage;
    }

    public void setInventoryResultMessage(InventoryResultMessage inventoryResultMessage) {
        this.inventoryResultMessage = inventoryResultMessage;
    }

    public BuildingResultMessage getBuildingResultMessage() {
        return buildingResultMessage;
    }

    public void setBuildingResultMessage(BuildingResultMessage buildingResultMessage) {
        this.buildingResultMessage = buildingResultMessage;
    }

    public RedirectMessage getRedirectMessage() {
        return redirectMessage;
    }

    public void setRedirectMessage(RedirectMessage redirectMessage) {
        this.redirectMessage = redirectMessage;
    }

    public LevelUpResultMessage getLevelUpResultMessage() {
        return levelUpResultMessage;
    }

    public void setLevelUpResultMessage(LevelUpResultMessage levelUpResultMessage) {
        this.levelUpResultMessage = levelUpResultMessage;
    }
}
