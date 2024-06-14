package com.jwt.demo.userandrole;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
//	@Autowired
//	private UserDetails userDetail;
//	@Bean
//	public static PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//		return configuration.getAuthenticationManager();
//	}

//	@Bean
//	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder)
//			throws Exception {
//		AuthenticationManagerBuilder authenticationManagerBuilder = http
//				.getSharedObject(AuthenticationManagerBuilder.class);
//		authenticationManagerBuilder.userDetailsService(userDetail).
//		return authenticationManagerBuilder.build();
//	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().requestMatchers("/api/**"
				, "/home/**").permitAll()
				.anyRequest().authenticated();
		http.headers().frameOptions().disable();
		return http.build();
	}
	
	
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests()
//				.requestMatchers("/api/ads/**", "/api/history/**", "/api/notifications/**", "/api/pay/**",
//						"/api/support/**", "/api/users/**", "/api/comments/**", "/api/access/**", "/api/request/**",
//						"/api/refund/**", "/api/price/**")
//				.permitAll().anyRequest().authenticated();
//		http.headers().frameOptions().disable();
//		return http.build();
//	}
	

//	@Bean
//	public CommandLineRunner demo(RoleRepository roleRepo) {
//		return (args) -> {
//			if (!roleRepo.existsByName("ROLE_ADMIN")) {
//				Role role = new Role();
//				role.setName("ROLE_ADMIN");
//				roleRepo.save(role);
//			}
//		};
//	}
}
