//package com.funfair.api.security;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.funfair.api.account.userrole.UserRole;
//import com.funfair.api.account.userrole.UserRoleRepository;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//	@Autowired
//	private JwtUtil jwtUtil;
//
//	@Autowired
//	private UserRoleRepository userRoleRepository;
//	@Override
//	protected boolean shouldNotFilter(HttpServletRequest request) {
//
//		String path = request.getServletPath();
//
//		return path.startsWith("/v3/api-docs")
//			|| path.startsWith("/swagger-ui")
//			|| path.equals("/swagger-ui.html")
//			|| path.startsWith("/account/user/")
//			|| path.startsWith("/auth");
//	}
//
//	@Override	
//	protected void doFilterInternal(
//			HttpServletRequest request,
//			HttpServletResponse response,
//			FilterChain filterChain)
//			throws ServletException, IOException {
//
//		String header = request.getHeader("Authorization");
//
//		if (header == null || !header.startsWith("Bearer ")) {
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token missing");
//			return;
//		}
//
//		String token = header.substring(7);
//
//		if (!jwtUtil.validateToken(token)) {
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//			return;
//		}
//
//		String userId = jwtUtil.extractUserId(token);
//
//		List<UserRole> roles = userRoleRepository.findByUserUserId(userId);
//
//		List<GrantedAuthority> authorities = roles.stream()
//			    .map(r -> (GrantedAuthority)
//			        new SimpleGrantedAuthority("ROLE_" + r.getRole().getRoleName()))
//			    .toList();
//
//		Authentication authentication =
//				new UsernamePasswordAuthenticationToken(userId, null, authorities);
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		filterChain.doFilter(request, response);
//	}
//}
