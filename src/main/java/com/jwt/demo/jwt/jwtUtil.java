package com.jwt.demo.jwt;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import com.jwt.demo.userandrole.HomeEntity;
import com.jwt.demo.userandrole.HomeReposistory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class jwtUtil {

	private final HomeReposistory homeReposistory;

	private final String secret_key = "Deqzfgkivkalsuetyf=nwxxn12ndiendi34mnvnbxvsgeirolfjfuywbqhbwuxnnfkfpogmmvbfmvormvkrmvjvnmtovmtmgvmgitmvtimggitjgitmfjswe432=0";
	private long accessTokenValidity = 60 ;
	private JwtParser jwtParser;
	private final String TOKEN_HEADER = "Authorization";
	private final String TOKEN_PREFIX = "Bearer";

	public jwtUtil(HomeReposistory homeReposistory) {

		this.jwtParser = Jwts.parser().setSigningKey(secret_key);
		this.homeReposistory = homeReposistory;
	}

	public String createToken(HomeEntity entity) {

		Claims claims = Jwts.claims().setSubject(entity.getEmailId());
//		claims.put("username", entity.getUsername());
//		claims.put("mobileNo", entity.getMobileNo());

		Date tokenCreatetime = new Date();
		Date tokenValidity = new Date(tokenCreatetime.getTime() + TimeUnit.SECONDS.toMillis(accessTokenValidity));

		return Jwts.builder().setClaims(claims).setExpiration(tokenValidity)
				.signWith(SignatureAlgorithm.HS256, secret_key).compact();

	}

	public String extractRoles(String token) {
		Claims claims = decodetoken(token);
		Object object = claims.get("roles");
		return token;
	}

	private Claims parseJwtClaims(String token) {
		return jwtParser.parseClaimsJws(token).getBody();
	}

	public Claims resolveClaims(HttpServletRequest req) {
		try {
			String token = resolveToken(req);
			if (token != null) {
				return parseJwtClaims(token);
			}
			return null;
		} catch (ExpiredJwtException ex) {
			req.setAttribute("expired", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			req.setAttribute("invalid", ex.getMessage());
			throw ex;
		}
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(TOKEN_HEADER);
		if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	public Claims decodetoken(String token) {
		try {
			System.out.println("Check point 101");
//			Claims claims = Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();

			JwtParser parser = Jwts.parser().setSigningKey(secret_key);
			Jws<Claims> jws = parser.parseClaimsJws(token);
			Claims claims = jws.getBody();

			System.out.println("Check point 102");
			System.out.println("Getting cliams==>" + claims.getExpiration());
			return claims;
		} catch (Exception e) {
			// Handle other exceptions (invalid token, signature issues, etc.)
			System.out.println("Invalid token: " + e.getMessage());
		}

		return null;
	}

	public boolean validateClaims(Claims claims) throws AuthenticationException {
		try {
//			System.out.println("Check exp with clamin "+claims.getExpiration());
			return claims.getExpiration().after(new Date());
		} catch (Exception e) {
			throw e;
		}
	}

	public String getEmail(Claims claims) {
		return claims.getSubject();
	}

	private List<String> getRoles(Claims claims) {
		return (List<String>) claims.get("roles");
	}

	public HomeEntity getUserFromToken(String token) {
		System.out.println(token);
		Claims claims = decodetoken(token);
		System.err.println(claims);
		String email = claims.getSubject();
		System.err.println(email);
		HomeEntity user = homeReposistory.findByEmailId(email);
		System.out.println("user:::: " + user);
		return user;
	}

	public Date getExpirationDateFromToken(String token) {
		Claims parseJwtClaims = parseJwtClaims(token);
		System.out.println("Check Expiration" + parseJwtClaims.getExpiration());
		return parseJwtClaims.getExpiration();
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

}
