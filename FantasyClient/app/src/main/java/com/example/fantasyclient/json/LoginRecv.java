package com.example.fantasyclient.json;

public class LoginRecv extends JsonBase{
    private int wid;
    private String error_msg;
    private String status;

    public LoginRecv() {
        super();
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

}
