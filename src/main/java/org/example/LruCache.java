package org.example;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class LruCache {

    int capacity;
    Map<Integer, Integer> cache;
    ArrayDeque<Integer> insertedKeys;

    public LruCache(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("LruCache requires a positive capacity initializer (provided: " + capacity + ").");
        }
        this.capacity = capacity;
        cache = new HashMap<>(capacity);
        insertedKeys = new ArrayDeque<>(capacity);
    }

    public LruCache() {
        this(0);
    }

    public void put(int key, int value) {
        if (key < 0) {
            throw new IllegalArgumentException("Required positive key (provided: " + key + ")");
        }

        if (capacity == 0) {
            return;
        }

        if (cache.containsKey(key)) {
            cache.put(key, value);
            insertedKeys.remove(key);
        } else {
            if (insertedKeys.size() == capacity) {
                var toRemoveKey = insertedKeys.removeFirst();
                cache.remove(toRemoveKey);
            }
            cache.put(key, value);
        }

        insertedKeys.addLast(key);
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            insertedKeys.remove(key);
            insertedKeys.addLast(key);
            return cache.get(key);
        }

        return -1;
    }
}
