package com.dpudov.server.internals;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private static final Map<String, char[]> cacheMap = new HashMap<>();

    public synchronized static void writeToCache(String location, char[] content) {
        cacheMap.put(location, content);
    }

    public static char[] serveFromCache(String location) {
        return cacheMap.getOrDefault(location, null);
    }
}
