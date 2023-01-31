package com.request.api.dto.admin.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
	
	@NotNull(message = "The name of admin is mandatory")
	@Size(message = "The name of admin must be between two and fifty characters", min = 2, max = 50)
		private String name;
	
	@NotNull(message = "The email of admin is mandatory")
	@Size(message = "The email of admin must be between two and ninety characters", min = 2, max = 90)
	private String email;
	
	@NotNull(message = "The password of admin is mandatory")
	@Size(message = "The password of admin must be between two and ninety characters", min = 2, max = 90)
	private String password;
	
}
