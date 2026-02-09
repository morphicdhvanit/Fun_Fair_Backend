//package com.funfair.api.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//@Configuration
//@EnableMethodSecurity
//public class SecurityConfig {
//
//	@Autowired
//	private JwtAuthenticationFilter jwtFilter;
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		System.out.println("Configuring security filter chain...");
//
//		http.csrf(csrf -> csrf.disable())
//			.authorizeHttpRequests(auth -> auth
//				// Swagger URLs
//				.requestMatchers(
//					"/v3/api-docs/**",
//					"/swagger-ui/**",
//					"/swagger-ui.html",
//					"/account/user/otp-send"
//				).permitAll()
//
//				// Auth APIs
//				.requestMatchers("/auth/**").permitAll()
//
//				// Everything else secured
//				.anyRequest().authenticated()
//			)
//			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//		return http.build();
//	}
//}
