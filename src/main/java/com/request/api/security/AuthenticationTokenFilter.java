package com.request.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.request.api.model.User;
import com.request.api.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class AuthenticationTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	private UserRepository userRepository;

	public AuthenticationTokenFilter(TokenService tokenService, UserRepository repository) {
		this.tokenService = tokenService;
		this.userRepository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = getToken(request);
		
		boolean valid = tokenService.isValidToken(token);
		
		if (valid) {
			authenticateClient(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void authenticateClient(String token) {
		
		Long idUser = tokenService.getUserId(token);
		
		User user = userRepository.findById(idUser).get();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}