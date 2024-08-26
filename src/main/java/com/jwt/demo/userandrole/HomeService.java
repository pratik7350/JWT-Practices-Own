package com.jwt.demo.userandrole;

import org.springframework.stereotype.Service;

import com.jwt.demo.jwt.AuthTokenCache;

@Service

public class HomeService {
	
	
	
	private final AuthTokenCache authTokenCache;

    public HomeService(AuthTokenCache authTokenCache) {
        this.authTokenCache = authTokenCache;
    }

    public String authenticateUser(Long userId) {
        String token = authTokenCache.getToken(userId);
        if (token != null) {
            return token;
        }
        // Here you would call the external authentication service
        authTokenCache.setToken(userId, token);
        return token;
    }

}
