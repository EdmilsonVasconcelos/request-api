package com.request.api.controller;

import com.request.api.dto.admin.request.AdminDTO;
import com.request.api.dto.admin.request.ChangePasswordRequestDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AdminControllerTest {

    public static final String ADMIN = "admin";

    public static final String ADMIN_EMAIL = "admin@gmail.com";

    public static final long ID = 1L;

    public static final String PASSWORD = "123123";

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    private Admin admin;

    private Admin adminSaved;

    private AdminDTO adminDTO;

    private AdminSavedDTO adminSavedDTO;

    private ChangePasswordRequestDTO changePasswordRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminController = new AdminController(adminService);
        startAdmin();
    }

    @Test
    void getAllAdminsShouldReturnListOfAdmins() {
        when(adminService.getAllAdmins()).thenReturn(List.of(adminSaved));

        ResponseEntity<List<AdminSavedDTO>> response = adminController.getAllAdmins();

        assertNotNull(response);

        assertEquals(1, response.getBody().size());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ADMIN, response.getBody().get(0).getName());

        assertEquals(ADMIN_EMAIL, response.getBody().get(0).getEmail());
    }

    @Test
    void changePassword() {
        when(adminService.changePassword(any())).thenReturn(adminSaved);

        ResponseEntity<AdminSavedDTO> response = adminController.changePassword(changePasswordRequestDTO);

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ADMIN, response.getBody().getName());

        assertEquals(ADMIN_EMAIL, response.getBody().getEmail());
    }

    @Test
    void saveAdmin() {
        when(adminService.saveAdmin(any())).thenReturn(adminSaved);

        ResponseEntity<AdminSavedDTO> response = adminController.saveAdmin(adminDTO);

        assertNotNull(response);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(ADMIN, response.getBody().getName());

        assertEquals(ADMIN_EMAIL, response.getBody().getEmail());
    }

    @Test
    void deleteAdmin() {
    }

    private void startAdmin() {
        admin = new Admin(null, ADMIN, ADMIN_EMAIL, PASSWORD, null, null, null);
        adminDTO = new AdminDTO(ADMIN, ADMIN_EMAIL, PASSWORD);
        adminSaved = new Admin(ID, ADMIN, ADMIN_EMAIL, PASSWORD, List.of(), LocalDateTime.now(), LocalDateTime.now());
        adminSavedDTO = new AdminSavedDTO(ID, ADMIN, ADMIN_EMAIL);
        changePasswordRequestDTO = new ChangePasswordRequestDTO(PASSWORD);
    }
}