package com.example.fantasyclient.json

import org.junit.Test;

class JsonHandlerTest {

    @Test
    public void test_JsonHandler(){
        testCheckTerrainSend();
    }

    private testCheckTerrainSend(){
        CheckTerrainSend target = new CheckTerrainSend("check_terrain", "grass", 1);
        JsonHandler jsonHandler = new JsonHandler(target);
        System.out.println(jsonHandler.serialize());
    }
}
