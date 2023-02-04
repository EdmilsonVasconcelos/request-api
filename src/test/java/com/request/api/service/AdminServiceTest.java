package com.request.api.service;

import com.request.api.model.Admin;
import com.request.api.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private ModelMapper modelMapper;

    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAdmin();
    }

    @Test
    void getAllAdminsShouldReturnListOfAdmins() {
        when(adminRepository.findAll()).thenReturn(List.of(admin));

        List<Admin> response = adminService.getAllAdmins();

        assertNotNull(response);

        assertEquals(1L, response.get(0).getId());

        assertEquals("admin", response.get(0).getName());

        assertEquals("admin@gmail.com", response.get(0).getEmail());
    }

    @Test
    void getAllAdminsShouldReturnEmptyList() {
        when(adminRepository.findAll()).thenReturn(List.of());

        List<Admin> response = adminService.getAllAdmins();

        assertEquals(0, response.size());
    }

    @Test
    void saveAdmin() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void deleteAdmin() {
    }

    private void startAdmin() {
        admin = new Admin(1L, "admin", "admin@gmail.com", "123123", List.of(), LocalDateTime.now(), LocalDateTime.now());
    }
}