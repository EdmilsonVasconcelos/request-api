package com.request.api.service;

import com.request.api.dto.admin.request.AdminDTO;
import com.request.api.dto.admin.request.ChangePasswordRequestDTO;
import com.request.api.exception.AdminExistsException;
import com.request.api.model.Admin;
import com.request.api.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminServiceTest {

    public static final String ADMIN = "admin";

    public static final String ADMIN_EMAIL = "admin@gmail.com";

    public static final String PASSWORD = "123123";

    public static final long ID = 1L;

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    private Admin admin;

    private Admin adminSaved;

    private AdminDTO adminDTO;
    
    private ChangePasswordRequestDTO changePasswordRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminService = new AdminService(adminRepository);
        startMocks();
    }

    @Test
    void getAllAdminsShouldReturnListOfAdmins() {
        when(adminRepository.findAll()).thenReturn(List.of(adminSaved));

        List<Admin> response = adminService.getAllAdmins();

        assertNotNull(response);

        assertEquals(ID, response.get(0).getId());

        assertEquals(ADMIN, response.get(0).getName());

        assertEquals(ADMIN_EMAIL, response.get(0).getEmail());
    }

    @Test
    void getAllAdminsShouldReturnEmptyList() {
        when(adminRepository.findAll()).thenReturn(List.of());

        List<Admin> response = adminService.getAllAdmins();

        assertEquals(0, response.size());
    }

    @Test
    void saveAdminShouldReturnAdminSaved() {
        when(adminRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        when(adminRepository.save(any())).thenReturn(adminSaved);

        Admin adminSaved = adminService.saveAdmin(adminDTO);

        assertNotNull(adminSaved);

        assertEquals(ADMIN, adminSaved.getName());

        assertEquals(ADMIN_EMAIL, adminSaved.getEmail());
    }

    @Test
    void saveAdminShouldReturnExceptionWhenAdminExists() {
        when(adminRepository.findByEmail(anyString())).thenReturn(Optional.of(adminSaved));

        var exception = assertThrows(AdminExistsException.class, () -> adminService.saveAdmin(adminDTO));

        assertEquals(AdminExistsException.class, exception.getClass());

        assertEquals("Admin with email admin@gmail.com exists.", exception.getMessage());

        verify(adminRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void changePasswordShouldUpdateWithSuccess() {
        Authentication mockAuth = Mockito.mock(Authentication.class);
        Mockito.when(mockAuth.getPrincipal()).thenReturn(adminSaved);

        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuth);
        SecurityContextHolder.setContext(mockSecurityContext);

        when(adminRepository.findByEmail(ADMIN_EMAIL)).thenReturn(Optional.of(adminSaved));

        when(adminRepository.save(any())).thenReturn(adminSaved);

        Admin response = adminService.changePassword(changePasswordRequestDTO);

        assertNotNull(response);

        assertEquals(ADMIN, response.getName());

        assertEquals(ADMIN_EMAIL, response.getEmail());
    }

    @Test
    void deleteAdmin() {
    }

    private void startMocks() {
        admin = new Admin(null, ADMIN, ADMIN_EMAIL, PASSWORD, null, null, null);
        adminSaved = new Admin(ID, ADMIN, ADMIN_EMAIL, PASSWORD, List.of(), LocalDateTime.now(), LocalDateTime.now());
        adminDTO = new AdminDTO(ADMIN, ADMIN_EMAIL, PASSWORD);
        changePasswordRequestDTO = new ChangePasswordRequestDTO(PASSWORD);
    }
}