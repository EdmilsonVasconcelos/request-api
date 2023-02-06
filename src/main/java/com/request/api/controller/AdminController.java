package com.request.api.controller;

import com.request.api.dto.admin.request.AdminDTO;
import com.request.api.dto.admin.request.ChangePasswordRequestDTO;
import com.request.api.dto.admin.response.AdminSavedDTO;
import com.request.api.model.Admin;
import com.request.api.service.AdminService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.request.api.dto.admin.response.AdminSavedDTO.toAdminSavedDTO;
import static com.request.api.dto.admin.response.AdminSavedDTO.toListAdminSavedDTO;
import static com.request.api.model.Admin.toDomain;

@RequestMapping("/v1/admin")
@RestController
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@GetMapping
	@Cacheable(value = "listAdmins")
	public ResponseEntity<List<AdminSavedDTO>> getAllAdmins() {
		List<Admin> admins = adminService.getAllAdmins();
		return ResponseEntity.ok(toListAdminSavedDTO(admins));
	}

	@PostMapping
	@CacheEvict(value = "listAdmins", allEntries = true)
	public ResponseEntity<AdminSavedDTO> saveAdmin(@Valid @RequestBody AdminDTO request) {
		Admin admin = adminService.saveAdmin(toDomain(request));

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(admin.getId())
				.toUri();

		return ResponseEntity.created(uri).body(toAdminSavedDTO(admin));
	}

	@PutMapping(value = "/change-password")
	@CacheEvict(value = "listAdmins", allEntries = true)
	public ResponseEntity<AdminSavedDTO> changePassword(@Valid @RequestBody ChangePasswordRequestDTO request) {
		Admin admin = adminService.changePassword(request.getPassword());

		return ResponseEntity.ok(toAdminSavedDTO(admin));
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = "listAdmins", allEntries = true)
	public ResponseEntity<AdminSavedDTO> deleteAdmin(@PathVariable Long id) {
		adminService.deleteAdmin(id);
		return ResponseEntity.noContent().build();
	} 
}
