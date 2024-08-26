package com.jwt.demo.userandrole;

import java.text.ParseException;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.demo.jwt.AuthTokenCache;
import com.jwt.demo.jwt.jwtUtil;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private HomeReposistory homeReposistory;


	@Autowired
	private AuthTokenCache authTokenCache;

	@Autowired
	private jwtUtil jwtUtil;
	
	@Autowired
	private HomeService homeService;

	@PostMapping("/save")
	public ResponseEntity<?> saveData(@RequestBody HomeEntity entity) {

		HomeEntity save = homeReposistory.save(entity);

		return ResponseEntity.ok(save);

	}

	@PostMapping("/login")
	public ResponseEntity<?> authicateUser(@RequestBody LoginDto dto)
			throws ParseException, JsonMappingException, JsonProcessingException {
		try {
			System.out.println("Check Point 1:::::");


			HomeEntity user = new HomeEntity(dto.getEmailId());

			String token = jwtUtil.createToken(user);
		

			JWT jwt = JWTParser.parse(token);
			System.out.println("Header: " + jwt.getHeader().toJSONObject());
			System.out.println("Payload: " + jwt.getJWTClaimsSet().toJSONObject());
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode idTokenJson = objectMapper.readTree(jwt.getJWTClaimsSet().toString());
			String email = idTokenJson.get("sub").asText();
			System.out.println("email: " + email);
			HomeEntity user1 = homeReposistory.findByEmailId(email);
			authTokenCache.setToken(user1.getUserId(), token);



			if (user1 != null) {
				System.out.println("Check Point 6:::: ");

				LoginRes loginRes = new LoginRes(dto.getEmailId(), token);

				return ResponseEntity.ok(loginRes);
			} 

		} catch (BadCredentialsException e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/token/{token}")
	public ResponseEntity<?> checkTokenExp(@PathVariable("token") String token)
			throws AuthenticationException, ParseException {

		Claims decodetoken = jwtUtil.decodetoken(token);
		if (decodetoken != null) {
			Boolean tokenExpired = jwtUtil.validateClaims(decodetoken);
			return ResponseEntity.ok(tokenExpired);
		} else {
			System.out.println("Inside else");
			return ResponseEntity.ok(false);
		}

	}

	
	@GetMapping("/getUserData/{userId}")
	public ResponseEntity<?> getUserData(@PathVariable  Long userId) {

		try {

			String token = homeService.authenticateUser(userId);
			System.out.println("Check token:: "+token);
			JWT jwt = JWTParser.parse(token);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode idTokenJson = objectMapper.readTree(jwt.getJWTClaimsSet().toString());
			String email = idTokenJson.get("sub").asText();
			HomeEntity user1 = homeReposistory.findByEmailId(email);
			if(userId==user1.getUserId()) {
				return ResponseEntity.ok(user1);
			}else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
						
			
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
