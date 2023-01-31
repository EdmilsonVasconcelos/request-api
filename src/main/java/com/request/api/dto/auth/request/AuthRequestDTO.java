package com.request.api.dto.auth.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Data;

@Data
public class AuthRequestDTO {
	
	@NotNull(message = "The email of user is mandatory")
	@Size(message = "The email of user must be between two and fifty characters", min = 2, max = 50)
	private String email;
	
	@NotNull(message = "The password of user is mandatory")
	@Size(message = "The password of user must be between two and fifty characters", min = 2, max = 1000)
	private String password;
	
	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}

}
