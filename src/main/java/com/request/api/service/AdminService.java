package com.request.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.request.api.dto.admin.request.ChangePasswordRequestDTO;
import com.request.api.dto.admin.request.AdminDTO;
import com.request.api.dto.admin.response.AdminSavedDTO;
import com.request.api.exception.AdminExistsException;
import com.request.api.exception.AdminNotExistException;
import com.request.api.model.Admin;
import com.request.api.repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminService {
	
	private static final String ADMIN_WITH_EMAIL_EXIST = "Admin with email %s exist.";
	
	private static final String ADMIN_WITH_EMAIL_NOT_EXIST = "Admin with email %s not exist.";
	
	private static final String ADMIN_WITH_ID_NOT_EXIST = "Admin with id %s not exist.";
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AdminRepository adminRepository;
	
	public List<AdminSavedDTO> getAllAdmins() {
		
		List<Admin> admins = adminRepository.findAll();
		
		List<AdminSavedDTO> response = new ArrayList<>();
		
		admins.forEach(admin -> {
			AdminSavedDTO adminSaved = mapper.map(admin, AdminSavedDTO.class);
			response.add(adminSaved);
		});
		
		return response;
		
	}
	
	public AdminSavedDTO saveAdmin(AdminDTO request) {
		checkExistAdmin(request.getEmail());
		
		Admin adminToSave = mapper.map(request, Admin.class);
		
		adminToSave.setPassword(new BCryptPasswordEncoder().encode(adminToSave.getPassword().toString()));
		
		Admin adminSaved = adminRepository.save(adminToSave);
		
		AdminSavedDTO response = mapper.map(adminSaved, AdminSavedDTO.class);
		
		return response;
	}
	
	public AdminSavedDTO changePassword(ChangePasswordRequestDTO request) {
		String userLogged = getEmailAdminLogged();
		
		Admin adminToSave = getAdminByEmail(userLogged);
		
		adminToSave.setPassword(new BCryptPasswordEncoder().encode(request.getPassword().toString()));
		
		Admin adminSaved = adminRepository.save(adminToSave);
		
		AdminSavedDTO response = mapper.map(adminSaved, AdminSavedDTO.class);
		
		return response;
	}
	
	private String getEmailAdminLogged() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
		    return ((UserDetails)principal).getUsername();
		} else {
		    return principal.toString();
		}
	}
	
	public void deleteAdmin(Long idAdmin) {
		checkExistAdmin(idAdmin);
		adminRepository.deleteById(idAdmin);
	}
	
	private Admin getAdminByEmail(String email) {
		return adminRepository.findByEmail(email).orElseThrow(() -> new AdminNotExistException(String.format(ADMIN_WITH_EMAIL_NOT_EXIST, email)));
	}

	private void checkExistAdmin(String email) {
		Optional<Admin> admin = adminRepository.findByEmail(email);
		
		if(admin.isPresent()) {
			throw new AdminExistsException(String.format(ADMIN_WITH_EMAIL_EXIST, email));
		}
	}
	
	private void checkExistAdmin(Long idUser) {
		Optional<Admin> admin = adminRepository.findById(idUser);
		
		if(!admin.isPresent()) {
			throw new AdminNotExistException(String.format(ADMIN_WITH_ID_NOT_EXIST, idUser));
		}
	}

}