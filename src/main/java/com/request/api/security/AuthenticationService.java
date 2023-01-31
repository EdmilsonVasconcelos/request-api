package com.request.api.security;

import java.util.Optional;

import com.request.api.model.Admin;
import com.request.api.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationService implements UserDetailsService{
	
	private static final String INCORRECT_INFORMATIONS_FOR_EMAIL = "Incorrect informations for email %s.";
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Admin> admin = adminRepository.findByEmail(username);
		
		if(!admin.isPresent()) {

			log.debug("AuthenticationService.loadUserByUsername - Finish - Email:  [{}], Message [{}]", username, INCORRECT_INFORMATIONS_FOR_EMAIL);

			throw new UsernameNotFoundException(INCORRECT_INFORMATIONS_FOR_EMAIL);
			
		}

		return admin.get();

	}
}
