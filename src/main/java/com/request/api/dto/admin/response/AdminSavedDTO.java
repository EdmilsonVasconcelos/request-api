package com.request.api.dto.admin.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminSavedDTO {
	
	private Long id;
	
	private String name;
	
	private String email;
	
}
