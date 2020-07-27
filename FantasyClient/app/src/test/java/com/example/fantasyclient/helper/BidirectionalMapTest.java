package com.example.fantasyclient.helper;

import org.junit.Test;

import static org.junit.Assert.*;

public class BidirectionalMapTest {

    private BidirectionalMap<String, Integer> testMap = new BidirectionalMap<>();

    @Test
    public void testBidirectionalMap() {
        //map is initialized to be empty
        assertTrue(testMap.isEmpty());
        //add an element to map
        testMap.put("gold", 100);
        assertEquals((int)testMap.get("gold"),100);
        assertEquals(testMap.getKey(100), "gold");
        //update the element in map
        testMap.put("gold", 200);
        assertEquals(testMap.getKey(200), "gold");
        //add another element
        testMap.put("food", 300);
        assertEquals(testMap.getKey(300), "food");
        //remove elements
        testMap.remove("food");
        assertNull(testMap.getKey(300));
        testMap.removeByValue(200);
        assertNull(testMap.getKey(200));
        assertTrue(testMap.isEmpty());
    }
}