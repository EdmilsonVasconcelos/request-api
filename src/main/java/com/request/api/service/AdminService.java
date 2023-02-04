package com.request.api.service;

import com.request.api.dto.admin.request.AdminDTO;
import com.request.api.dto.admin.request.ChangePasswordRequestDTO;
import com.request.api.exception.AdminExistsException;
import com.request.api.exception.AdminNotExistException;
import com.request.api.model.Admin;
import com.request.api.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.request.api.model.Admin.toDomain;

@Slf4j
@Service
public class AdminService {
	
	private static final String ADMIN_WITH_EMAIL_EXISTS = "Admin with email %s exists.";
	
	private static final String ADMIN_WITH_EMAIL_NOT_EXISTS = "Admin with email %s not exists.";
	
	private static final String ADMIN_WITH_ID_NOT_EXISTS = "Admin with id %s not exists.";
	
	@Autowired
	private ModelMapper mapper;
	
	private final AdminRepository adminRepository;

	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
	
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}
	
	public Admin saveAdmin(AdminDTO request) {
		checkAdminExists(request.getEmail());
		
		Admin adminToSave = toDomain(request);
		
		adminToSave.setPassword(new BCryptPasswordEncoder().encode(adminToSave.getPassword()));

		return adminRepository.save(adminToSave);
	}
	
	public Admin changePassword(ChangePasswordRequestDTO request) {
		String userLogged = getEmailAdminLogged();
		
		Admin adminToSave = getAdminByEmail(userLogged);
		
		adminToSave.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
		
		return adminRepository.save(adminToSave);
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
		checkAdminExists(idAdmin);

		adminRepository.deleteById(idAdmin);
	}
	
	private Admin getAdminByEmail(String email) {
		return adminRepository.findByEmail(email)
				.orElseThrow(() -> new AdminNotExistException(String.format(ADMIN_WITH_EMAIL_NOT_EXISTS, email)));
	}

	private void checkAdminExists(String email) {
		Optional<Admin> admin = adminRepository.findByEmail(email);
		
		if(admin.isPresent()) {
			throw new AdminExistsException(String.format(ADMIN_WITH_EMAIL_EXISTS, email));
		}
	}
	
	private void checkAdminExists(Long idUser) {
		Optional<Admin> admin = adminRepository.findById(idUser);
		
		if(admin.isEmpty()) {
			throw new AdminNotExistException(String.format(ADMIN_WITH_ID_NOT_EXISTS, idUser));
		}
	}

}