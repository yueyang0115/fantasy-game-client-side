package com.example.fantasyclient.json;

public class LoginSend extends JsonBase{
    private String username;
    private String password;

    public LoginSend(String type, String username, String password) {
        super();
        this.type = type;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
