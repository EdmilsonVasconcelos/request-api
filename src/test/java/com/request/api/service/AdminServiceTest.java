package com.request.api.service;

import com.request.api.dto.admin.request.AdminDTO;
import com.request.api.model.Admin;
import com.request.api.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminService = new AdminService(adminRepository);
        startAdmin();
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
    void changePassword() {
    }

    @Test
    void deleteAdmin() {
    }

    private void startAdmin() {
        admin = new Admin(null, ADMIN, ADMIN_EMAIL, PASSWORD, null, null, null);
        adminSaved = new Admin(ID, ADMIN, ADMIN_EMAIL, PASSWORD, List.of(), LocalDateTime.now(), LocalDateTime.now());
        adminDTO = new AdminDTO(ADMIN, ADMIN_EMAIL, PASSWORD);
    }
}