package com.example.fantasyclient.json;

import org.junit.Test;

public class JsonHandlerTest {
    @Test
    public void addition_isCorrect() {
        testCheckTerrainSend();
    }

    private void testCheckTerrainSend(){
        CheckTerrainSend target = new CheckTerrainSend("check_terrain", "grass", 1);
        JsonHandler jsonHandler = new JsonHandler(target);
        System.out.println(jsonHandler.serialize());
    }
}