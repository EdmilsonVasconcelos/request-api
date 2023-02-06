package com.request.api.service;

import com.request.api.dto.admin.request.AdminDTO;
import com.request.api.dto.admin.request.ChangePasswordRequestDTO;
import com.request.api.exception.AdminExistsException;
import com.request.api.exception.AdminNotExistException;
import com.request.api.model.Admin;
import com.request.api.repository.AdminRepository;
import com.request.api.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

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

    private Admin adminToSave;

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
        when(adminRepository.findAll()).thenReturn(List.of(admin));

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

        when(adminRepository.save(any())).thenReturn(admin);

        Admin adminSaved = adminService.saveAdmin(adminToSave);

        assertNotNull(adminSaved);

        assertEquals(ADMIN, adminSaved.getName());

        assertEquals(ADMIN_EMAIL, adminSaved.getEmail());
    }

    @Test
    void saveAdminShouldReturnExceptionWhenAdminExists() {
        when(adminRepository.findByEmail(anyString())).thenReturn(Optional.of(admin));

        var exception = assertThrows(AdminExistsException.class, () -> adminService.saveAdmin(adminToSave));

        assertEquals(AdminExistsException.class, exception.getClass());

        assertEquals("Admin with email admin@gmail.com exists.", exception.getMessage());

        verify(adminRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void changePasswordShouldUpdateWithSuccess() {
        SecurityUtils.MockAdminLogged(admin);

        when(adminRepository.findByEmail(ADMIN_EMAIL)).thenReturn(Optional.of(admin));

        when(adminRepository.save(any())).thenReturn(admin);

        Admin response = adminService.changePassword(changePasswordRequestDTO);

        assertNotNull(response);

        assertEquals(ADMIN, response.getName());

        assertEquals(ADMIN_EMAIL, response.getEmail());
    }

    @Test
    void changePasswordShouldThrowExceptionWhenAdminLoggedDoesNotExist() {
        SecurityUtils.MockAdminLogged(admin);

        when(adminRepository.findByEmail(ADMIN_EMAIL)).thenReturn(Optional.empty());

        AdminNotExistException exception = assertThrows(AdminNotExistException.class, () -> adminService.changePassword(changePasswordRequestDTO));

        assertEquals(AdminNotExistException.class, exception.getClass());

        assertEquals("Admin with email admin@gmail.com does not exist.", exception.getMessage());

        verify(adminRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void deleteAdminShouldDeleteWithSuccess() {
        when(adminRepository.findById(anyLong())).thenReturn(Optional.of(admin));

        adminService.deleteAdmin(ID);

        verify(adminRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteAdminShouldThrowExceptionWhenAdminLoggedDoesNotExist() {
        when(adminRepository.findById(anyLong())).thenReturn(Optional.empty());

        AdminNotExistException exception = assertThrows(AdminNotExistException.class, () -> adminService.deleteAdmin(ID));

        assertEquals(AdminNotExistException.class, exception.getClass());

        assertEquals("Admin with id 1 does not exist.", exception.getMessage());

        verify(adminRepository, times(1)).findById(anyLong());
    }

    private void startMocks() {
        admin = new Admin(ID, ADMIN, ADMIN_EMAIL, PASSWORD, List.of(), LocalDateTime.now(), LocalDateTime.now());
        adminToSave = new Admin(null, ADMIN, ADMIN_EMAIL, PASSWORD, null, null, null);
        adminDTO = new AdminDTO(ADMIN, ADMIN_EMAIL, PASSWORD);
        changePasswordRequestDTO = new ChangePasswordRequestDTO(PASSWORD);
    }
}