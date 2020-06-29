package com.example.fantasyclient.json;

public class LoginResultMessage {
    private int wid;
    private int id;
    private String error_msg;
    private String status;

    public LoginResultMessage() {
    }

    public LoginResultMessage(int wid, int id, String error_msg, String status) {
        this.wid = wid;
        this.id = id;
        this.error_msg = error_msg;
        this.status = status;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
