package com.request.api.dto.admin.response;

import com.request.api.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminSavedDTO {
	
	private Long id;
	
	private String name;
	
	private String email;


	public static AdminSavedDTO toAdminSavedDTO(Admin admin) {
		return AdminSavedDTO.builder()
				.id(admin.getId())
				.name(admin.getName())
				.email(admin.getEmail())
				.build();
	}

	public static List<AdminSavedDTO> toListAdminSavedDTO(List<Admin> admins) {
		return admins.stream()
				.map(admin -> AdminSavedDTO.builder()
						.id(admin.getId())
						.name(admin.getName())
						.email(admin.getEmail())
						.build())
				.collect(Collectors.toList());
	}
}
