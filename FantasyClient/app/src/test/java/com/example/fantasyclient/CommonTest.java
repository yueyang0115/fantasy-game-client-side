package com.example.fantasyclient;

import com.example.fantasyclient.json.CheckTerrainSend;
import com.example.fantasyclient.json.JsonHandler;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommonTest {
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