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

import static com.request.api.dto.admin.response.AdminSavedDTO.toListAdminSavedDTO;

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

	@PutMapping(value = "/change-password")
	@CacheEvict(value = "listAdmins", allEntries = true)
	public ResponseEntity<AdminSavedDTO> updatePassword(@Valid @RequestBody ChangePasswordRequestDTO request) {
		AdminSavedDTO adminSaved = adminService.changePassword(request);
		return ResponseEntity.ok(adminSaved);
	}
	
	@PostMapping
	@CacheEvict(value = "listAdmins", allEntries = true)
	public ResponseEntity<AdminSavedDTO> saveAdmin(@Valid @RequestBody AdminDTO request) {
		Admin admin = adminService.saveAdmin(request);

		AdminSavedDTO adminSavedDTO = AdminSavedDTO.toAdminSavedDTO(admin);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(adminSavedDTO.getId())
				.toUri();

		return ResponseEntity.created(uri).body(adminSavedDTO);
	}
	
	@DeleteMapping
	@CacheEvict(value = "listAdmins", allEntries = true)
	public ResponseEntity<AdminSavedDTO> deleteAdmin(@RequestParam Long idAdmin) {
		adminService.deleteAdmin(idAdmin);
		return ResponseEntity.noContent().build();
	} 
}
