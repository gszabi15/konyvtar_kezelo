package org.gszabi15.service;

import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.dto.UserDto;
import org.gszabi15.model.entity.User;
import org.gszabi15.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private AdminService adminService;

    private static final User user = new User(new UUID(123,123), "test", "test@t.com", "test123", "");


    @Test
    void create_shuldReturnOk() {
        User newadmin = new User(new UUID(123, 123), "newadmin","newadmin@example.com", "newadmin123", "ROLE_USER,ROLE_ADMIN");

        when(userRepository.save(newadmin)).thenReturn(newadmin);

        UserDto newadmindto = new UserDto(newadmin.getName(), newadmin.getEmail(), newadmin.getPassword(), newadmin.getRoles());
        when(mapper.userToDto(newadmin)).thenReturn(newadmindto);
        when(mapper.dtoToUser(newadmindto)).thenReturn(newadmin);

        UserDto result = adminService.create(newadmindto);

        assertNotNull(result);
        assertEquals(newadmin.getEmail(), result.getEmail());
        verify(userRepository).save(newadmin);
    }

    @Test
    void getByEmail_validEmail_shouldReturnOk() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserDto dto = new UserDto(user.getName(), user.getEmail(), user.getPassword(), user.getRoles());
        when(mapper.userToDto(user)).thenReturn(dto);

        UserDto result = adminService.getByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void deleteByEmail_validEmail_shuldReturnOk() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        adminService.deleteByEmail(user.getEmail());

        verify(userRepository).deleteByEmail(user.getEmail());
    }

    @Test
    void updateByEmail_validEmail_shuldReturnOk() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDto updatedDto = new UserDto("new_test", "t@test.com", "test123", "ROLE_USER");
        when(mapper.userToDto(user)).thenReturn(updatedDto);
        when(userRepository.save(user)).thenReturn(user);
        UserDto result = adminService.updateByEmail(user.getEmail(), updatedDto);
        assertNotNull(result);
        assertNotEquals(user.getEmail(), result.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void getAllUser_shuldReturnOk() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        UserDto dto = new UserDto(user.getName(), user.getEmail(), user.getPassword(), user.getRoles());
        when(mapper.userToDto(user)).thenReturn(dto);

        List<UserDto> result = adminService.getAllUser();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }
}