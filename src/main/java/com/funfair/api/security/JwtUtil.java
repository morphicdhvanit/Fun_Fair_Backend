//package com.funfair.api.security;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.Jwts;
//
//@Component
//public class JwtUtil {
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//	private final String SECRET = jwtSecret;
//
//	public String extractUserId(String token) {
//		return Jwts.parser()
//			.setSigningKey(SECRET)
//			.parseClaimsJws(token)
//			.getBody()
//			.getSubject();
//	}
//
//	public boolean validateToken(String token) {
//		try {
//			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//}
