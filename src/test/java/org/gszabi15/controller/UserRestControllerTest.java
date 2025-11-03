package org.gszabi15.controller;

import org.gszabi15.model.dto.UserDto;
import org.gszabi15.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    private UserDto user;

    @BeforeEach
    void setUp() {
        user = new UserDto("test", "test@t.com", "test123", "ROLE_USER");
    }

    @Test
    void create_shouldReturnOk() {
        when(userService.create(user)).thenReturn("User created successfully.");

        String result = userRestController.create(user);

        assertEquals("User created successfully.", result);
        verify(userService).create(user);
    }

    @Test
    void update_shouldReturnOk() {
        when(userService.update(user)).thenReturn("User updated successfully.");

        String result = userRestController.update(user);

        assertEquals("User updated successfully.", result);
        verify(userService).update(user);
    }

    @Test
    void delete_shouldReturnOk() {
        when(userService.delete()).thenReturn("User deleted successfully.");

        String result = userRestController.delete();

        assertEquals("User deleted successfully.", result);
        verify(userService).delete();
    }
}