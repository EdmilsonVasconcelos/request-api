package com.request.api.controller;

import com.request.api.dto.admin.response.AdminSavedDTO;
import com.request.api.model.Admin;
import com.request.api.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    private Admin admin;

    private AdminSavedDTO adminSavedDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminController = new AdminController(adminService);
        startAdmin();
    }

    @Test
    void getAllAdminsShouldReturnListOfAdmins() {
        when(adminService.getAllAdmins()).thenReturn(List.of(admin));

        ResponseEntity<List<AdminSavedDTO>> response = adminController.getAllAdmins();

        assertNotNull(response);

        assertEquals(ResponseEntity.class, response.getClass());

        assertEquals(1, response.getBody().size());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updatePassword() {
    }

    @Test
    void saveAdmin() {
    }

    @Test
    void deleteAdmin() {
    }

    private void startAdmin() {
        admin = new Admin(1L, "admin", "admin@gmail.com", "123123", List.of(), LocalDateTime.now(), LocalDateTime.now());
        adminSavedDTO = new AdminSavedDTO(1L, "admin", "admin@gmail.com");
    }
}