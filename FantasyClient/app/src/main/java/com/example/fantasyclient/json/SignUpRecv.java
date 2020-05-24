package com.example.fantasyclient.json;

public class SignUpRecv extends JsonBase{
    private String error_msg;
    private String status;

    public SignUpRecv() {
        super();
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
