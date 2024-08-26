package com.jwt.demo.jwt;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class AuthTokenCache {
	
	
	
	private final int maxSize;
    private final Map<Long, String> tokenCache;

    // Constructor to initialize the cache with a specified size
    public AuthTokenCache() {
        this.maxSize = 100; // Example size, you can adjust this
        this.tokenCache = new LinkedHashMap<Long, String>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Long, String> eldest) {
                return size() > maxSize;
            }
        };
    }

    // Retrieve a token from the cache
    public synchronized String getToken(Long key) {
    	  for (Map.Entry<Long, String> entry : tokenCache.entrySet()) {
              Long id = entry.getKey();
              String value = entry.getValue();
              System.out.println("Key: " + id + ", Token: " + value);
          }
        return tokenCache.get(key);
    }

    // Add a token to the cache
    public synchronized void setToken(Long key, String token) {
    	
    	 
        tokenCache.put(key, token);
        for (Map.Entry<Long, String> entry : tokenCache.entrySet()) {
            Long id = entry.getKey();
            String value = entry.getValue();
            System.out.println("Key: " + id + ", Token: " + value);
        }
    }

}
