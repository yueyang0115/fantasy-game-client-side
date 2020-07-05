package com.example.fantasyclient.helper;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;

public class BidirectionalMap<K,V> extends HashMap<K,V> {

    private Map<V,K> reverseMap = new HashMap<>();
    private static final String TAG = "BidirectionalMap";

    //add to both maps
    @Override
    public V put(K key, V value) {
        //remove from reverse map if put a new value to an existing key
        if(reverseMap.containsValue(key)){
            reverseMap.remove(get(key));
        }
        reverseMap.put(value, key);
        return super.put(key, value);
    }

    //remove from both maps
    @Override
    public V remove(Object k){
        V v = get(k);
        assertNotNull(v);
        reverseMap.remove(v);
        return super.remove(k);
    }

    public void removeByValue(Object v){
        K k = reverseMap.get(v);
        assertNotNull(k);
        super.remove(k);
        reverseMap.remove(v);
    }

    public K getKey(V value){
        return reverseMap.get(value);
    }
}
