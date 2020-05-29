package com.example.fantasyclient.json;

public class SignUpRequestMessage {
    private String username;
    private String password;

    public SignUpRequestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SignUpRequestMessage(){}

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
