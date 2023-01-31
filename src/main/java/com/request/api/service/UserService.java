package com.request.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.request.api.dto.user.request.ChangePasswordRequestDTO;
import com.request.api.dto.user.request.SaveUserDTO;
import com.request.api.dto.user.response.UserSavedDTO;
import com.request.api.exception.UserExistsException;
import com.request.api.exception.UserNotExistException;
import com.request.api.model.User;
import com.request.api.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	private static final String USER_WITH_EMAIL_EXIST = "User with email %s exist.";
	
	private static final String USER_WITH_EMAIL_NOT_EXIST = "User with email %s not exist.";
	
	private static final String USER_WITH_ID_NOT_EXIST = "User with id %s not exist.";
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserSavedDTO> getAllUsers() {
		
		List<User> users = userRepository.findAll();
		
		List<UserSavedDTO> response = new ArrayList<>();
		
		users.forEach(user -> {
			UserSavedDTO userSaved = mapper.map(user, UserSavedDTO.class);
			response.add(userSaved);
		});
		
		return response;
		
	}
	
	public UserSavedDTO saveUser(SaveUserDTO request) {
		checkExistUser(request.getEmail());
		
		User userToSave = mapper.map(request, User.class);
		
		userToSave.setPassword(new BCryptPasswordEncoder().encode(userToSave.getPassword().toString()));
		
		User userSaved = userRepository.save(userToSave);
		
		UserSavedDTO response = mapper.map(userSaved, UserSavedDTO.class);
		
		return response;
	}
	
	public UserSavedDTO changePassword(ChangePasswordRequestDTO request) {
		String userLogged = getEmailUserLogged();
		
		User userToSave = getUserByEmail(userLogged);
		
		userToSave.setPassword(new BCryptPasswordEncoder().encode(request.getPassword().toString()));
		
		User userSaved = userRepository.save(userToSave);
		
		UserSavedDTO response = mapper.map(userSaved, UserSavedDTO.class);
		
		return response;
	}
	
	private String getEmailUserLogged() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
		    return ((UserDetails)principal).getUsername();
		} else {
		    return principal.toString();
		}
	}
	
	public void deleteUser(Long idUser) {
		checkExistUser(idUser);
		userRepository.deleteById(idUser);
	}
	
	private User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistException(String.format(USER_WITH_EMAIL_NOT_EXIST, email)));
	}

	private void checkExistUser(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		
		if(user.isPresent()) {
			throw new UserExistsException(String.format(USER_WITH_EMAIL_EXIST, email));
		}
	}
	
	private void checkExistUser(Long idUser) {
		Optional<User> user = userRepository.findById(idUser);
		
		if(!user.isPresent()) {
			throw new UserNotExistException(String.format(USER_WITH_ID_NOT_EXIST, idUser));
		}
	}

}