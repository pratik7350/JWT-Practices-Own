package com.jwt.demo.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.demo.userandrole.HomeEntity;
import com.jwt.demo.userandrole.HomeReposistory;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
	
	private final jwtUtil jwtUtil;
	private final ObjectMapper objectMapper;
	private final HomeReposistory homeReposistory;
	
	

	public JWTAuthorizationFilter(com.jwt.demo.jwt.jwtUtil jwtUtil, ObjectMapper objectMapper,
			HomeReposistory homeReposistory) {
		super();
		this.jwtUtil = jwtUtil;
		this.objectMapper = objectMapper;
		this.homeReposistory = homeReposistory;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		  Map<String, Object> errorDetails = new HashMap<>();
		
		try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken != null) {
                Claims claims = jwtUtil.resolveClaims(request);

                if (claims != null && jwtUtil.validateClaims(claims)) {
                    String email = claims.getSubject();
                    // Fetch the user from the database to get the roles
                    HomeEntity user = homeReposistory.findByEmailId(email);

                    if (user != null) {
//                        Role roles = user.getRoles();
//
//                        // Prefix roles with "ROLE_" if necessary
//                        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority("ROLE_" + roles.getName());
//
//                        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        // Handle the case where the user is not found in the database
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        errorDetails.put("message", "User not found");
                        objectMapper.writeValue(response.getWriter(), errorDetails);
                        return;
                    }
                }
            }

        } catch (Exception e) {
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("details", e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            objectMapper.writeValue(response.getWriter(), errorDetails);

            return;
        }

        filterChain.doFilter(request, response);
    }
		
	

}
