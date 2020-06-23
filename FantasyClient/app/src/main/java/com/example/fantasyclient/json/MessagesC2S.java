package com.example.fantasyclient.json;

public class MessagesC2S {
    private LoginRequestMessage loginRequestMessage;
    private SignUpRequestMessage signUpRequestMessage;
    private PositionRequestMessage positionRequestMessage;
    private BattleRequestMessage battleRequestMessage;
    private AttributeRequestMessage attributeRequestMessage;
    private ShopRequestMessage shopRequestMessage;
    private InventoryRequestMessage inventoryRequestMessage;
    private BuildingRequestMessage buildingRequestMessage;

    //constructors
    public MessagesC2S() {
    }

    public MessagesC2S(ShopRequestMessage shopRequestMessage) {
        this.shopRequestMessage = shopRequestMessage;
    }

    public MessagesC2S(LoginRequestMessage loginRequestMessage) {
        this.loginRequestMessage = loginRequestMessage;
    }

    public MessagesC2S(SignUpRequestMessage signUpRequestMessage) {
        this.signUpRequestMessage = signUpRequestMessage;
    }

    public MessagesC2S(PositionRequestMessage positionRequestMessage) {
        this.positionRequestMessage = positionRequestMessage;
    }

    public MessagesC2S(BattleRequestMessage battleRequestMessage) {
        this.battleRequestMessage = battleRequestMessage;
    }

    public MessagesC2S(AttributeRequestMessage attributeRequestMessage) {
        this.attributeRequestMessage = attributeRequestMessage;
    }

    public MessagesC2S(InventoryRequestMessage inventoryRequestMessage) {
        this.inventoryRequestMessage = inventoryRequestMessage;
    }

    public MessagesC2S(BuildingRequestMessage buildingRequestMessage) {
        this.buildingRequestMessage = buildingRequestMessage;
    }

    //getter and setter

    public LoginRequestMessage getLoginRequestMessage() {
        return loginRequestMessage;
    }

    public void setLoginRequestMessage(LoginRequestMessage loginRequestMessage) {
        this.loginRequestMessage = loginRequestMessage;
    }

    public SignUpRequestMessage getSignUpRequestMessage() {
        return signUpRequestMessage;
    }

    public void setSignUpRequestMessage(SignUpRequestMessage signUpRequestMessage) {
        this.signUpRequestMessage = signUpRequestMessage;
    }

    public PositionRequestMessage getPositionRequestMessage() {
        return positionRequestMessage;
    }

    public void setPositionRequestMessage(PositionRequestMessage positionRequestMessage) {
        this.positionRequestMessage = positionRequestMessage;
    }

    public BattleRequestMessage getBattleRequestMessage() {
        return battleRequestMessage;
    }

    public void setBattleRequestMessage(BattleRequestMessage battleRequestMessage) {
        this.battleRequestMessage = battleRequestMessage;
    }

    public AttributeRequestMessage getAttributeRequestMessage() {
        return attributeRequestMessage;
    }

    public void setAttributeRequestMessage(AttributeRequestMessage attributeRequestMessage) {
        this.attributeRequestMessage = attributeRequestMessage;
    }

    public ShopRequestMessage getShopRequestMessage() {
        return shopRequestMessage;
    }

    public void setShopRequestMessage(ShopRequestMessage shopRequestMessage) {
        this.shopRequestMessage = shopRequestMessage;
    }

    public InventoryRequestMessage getInventoryRequestMessage() {
        return inventoryRequestMessage;
    }

    public void setInventoryRequestMessage(InventoryRequestMessage inventoryRequestMessage) {
        this.inventoryRequestMessage = inventoryRequestMessage;
    }

    public BuildingRequestMessage getBuildingRequestMessage() {
        return buildingRequestMessage;
    }

    public void setBuildingRequestMessage(BuildingRequestMessage buildingRequestMessage) {
        this.buildingRequestMessage = buildingRequestMessage;
    }
}
