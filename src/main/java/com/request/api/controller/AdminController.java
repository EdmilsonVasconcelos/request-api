package com.request.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.request.api.dto.admin.request.ChangePasswordRequestDTO;
import com.request.api.dto.admin.request.AdminDTO;
import com.request.api.dto.admin.response.AdminSavedDTO;
import com.request.api.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequestMapping("/v1/admin")
@AllArgsConstructor
@RestController
public class AdminController {
	
	private final AdminService adminService;
	
	@GetMapping
	@Cacheable(value = "listAdmins")
	public ResponseEntity<List<AdminSavedDTO>> getAllAdmins() {
		List<AdminSavedDTO> response = adminService.getAllAdmins();
		return ResponseEntity.ok(response);
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
		AdminSavedDTO adminSaved = adminService.saveAdmin(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(adminSaved.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(adminSaved);
	}
	
	@DeleteMapping
	@CacheEvict(value = "listAdmins", allEntries = true)
	public ResponseEntity<AdminSavedDTO> deleteAdmin(@RequestParam Long idAdmin) {
		adminService.deleteAdmin(idAdmin);
		return ResponseEntity.noContent().build();
	} 
}
