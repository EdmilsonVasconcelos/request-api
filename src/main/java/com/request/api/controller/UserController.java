package com.request.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.request.api.dto.user.request.ChangePasswordRequestDTO;
import com.request.api.dto.user.request.SaveUserDTO;
import com.request.api.dto.user.response.UserSavedDTO;
import com.request.api.service.UserService;
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

@RequestMapping("/v1/user")
@AllArgsConstructor
@RestController
public class UserController {
	
	private final UserService userService;
	
	@GetMapping
	@Cacheable(value = "listUsers")
	public ResponseEntity<List<UserSavedDTO>> getAllUsers() {
		List<UserSavedDTO> response = userService.getAllUsers();
		return ResponseEntity.ok(response);
	}
	
	@PutMapping(value = "/change-password")
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<UserSavedDTO> udpdatePassword(@Valid @RequestBody ChangePasswordRequestDTO request) {
		UserSavedDTO userSaved = userService.changePassword(request);
		return ResponseEntity.ok(userSaved);
	}
	
	@PostMapping
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<UserSavedDTO> saveUser(@Valid @RequestBody SaveUserDTO request) {
		UserSavedDTO userSaved = userService.saveUser(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userSaved.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(userSaved);
	}
	
	@DeleteMapping
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<UserSavedDTO> deleteUser(@RequestParam Long idUser) {
		userService.deleteUser(idUser);
		return ResponseEntity.noContent().build();
	} 
}
