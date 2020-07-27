package com.example.fantasyclient.json;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpResultMessage implements Serializable {
    private String error_msg;
    private String status;

    public SignUpResultMessage() {
    }

    public SignUpResultMessage(String error_msg, String status) {
        this.error_msg = error_msg;
        this.status = status;
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
