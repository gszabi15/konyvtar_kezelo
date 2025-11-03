package org.gszabi15.controller;

import org.gszabi15.model.dto.UserDto;
import org.gszabi15.service.AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminRestControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminRestController adminRestController;

    private UserDto user;

    @BeforeEach
    void setUp() {
        user = new UserDto("test", "test@t.com", "test123", "ROLE_USER");
    }

    @Test
    void create_shouldReturnOk() {
        when(adminService.create(user)).thenReturn(user);

        UserDto result = adminRestController.create(user);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        verify(adminService).create(user);
    }

    @Test
    void getByEmail_shouldReturnOk() {
        when(adminService.getByEmail(user.getEmail())).thenReturn(user);

        UserDto result = adminRestController.getByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        verify(adminService).getByEmail(user.getEmail());
    }

    @Test
    void updateByEmail_shouldReturnOk() {
        UserDto userDto = new UserDto("updatedName", user.getEmail(), user.getPassword(), user.getRoles());
        when(adminService.updateByEmail(eq(user.getEmail()), any(UserDto.class)))
                .thenReturn(userDto);

        UserDto result = adminRestController.updateByEmail(user.getEmail(), userDto);

        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());
        verify(adminService).updateByEmail(eq(user.getEmail()), any(UserDto.class));
    }

    @Test
    void deleteByEmail_shouldCallService() {
        doNothing().when(adminService).deleteByEmail(user.getEmail());

        adminRestController.deleteByEmail(user.getEmail());

        verify(adminService).deleteByEmail(user.getEmail());
    }

    @Test
    void getAllUser_shouldReturnList() {
        when(adminService.getAllUser()).thenReturn(List.of(user));

        List<UserDto> result = adminRestController.getAllUser();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user.getEmail(), result.get(0).getEmail());
        verify(adminService).getAllUser();
    }
}