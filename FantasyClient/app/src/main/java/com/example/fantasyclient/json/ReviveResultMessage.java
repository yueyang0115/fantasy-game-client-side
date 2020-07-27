package com.example.fantasyclient.json;

import java.io.Serializable;

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
