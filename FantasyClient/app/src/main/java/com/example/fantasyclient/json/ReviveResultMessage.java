package com.example.fantasyclient.json;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviveResultMessage implements Serializable {

    private String result;

    public ReviveResultMessage() {
    }

    public ReviveResultMessage(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
