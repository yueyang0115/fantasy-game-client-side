package com.example.fantasyclient.helper;

import java.util.HashMap;
import java.util.Map;

public class BidirectMap<K,V> extends HashMap<K,V> {

    private Map<V,K> reverseMap = new HashMap<V,K>();

    //add to both maps
    @Override
    public V put(K key, V value) {
        reverseMap.put(value, key);
        return super.put(key, value);
    }

    //remove from both maps
    @Override
    public V remove(Object k){
        reverseMap.remove(get(k));
        return super.remove(k);
    }

    public void removeByValue(Object v){
        super.remove(reverseMap.get(v));
        reverseMap.remove(v);
    }

    public K getKey(V value){
        return reverseMap.get(value);
    }
}
