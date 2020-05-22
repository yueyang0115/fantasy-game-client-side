package com.example.fantasyclient;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonHandler {

    static public String serialUserInfo(String username, String password, String type) {
        JSONObject jsonUserInfo = new JSONObject();
        try {
            jsonUserInfo.put("type", type);
            jsonUserInfo.put("username", username);
            jsonUserInfo.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonUserInfo.toString();
    }

    static public String serialLocation(double x, double y){
        JSONObject jsonLocation = new JSONObject();
        try {
            jsonLocation.put("type", "position");
            jsonLocation.put("x", x);
            jsonLocation.put("y", y);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonLocation.toString();
    }

}
