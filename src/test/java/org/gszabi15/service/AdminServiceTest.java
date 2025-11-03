package org.gszabi15.service;

import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.dto.UserDto;
import org.gszabi15.model.entity.User;
import org.gszabi15.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
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

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User(UUID.randomUUID(), "test", "test@t.com", "test123", "");
        userDto = new UserDto(user.getName(), user.getEmail(), user.getPassword(), user.getRoles());
    }

    @Test
    void create_shouldReturnUserDto() {
        User newAdmin = new User(UUID.randomUUID(), "newadmin", "newadmin@example.com", "newadmin123", "ROLE_USER,ROLE_ADMIN");
        UserDto newAdminDto = new UserDto(newAdmin.getName(), newAdmin.getEmail(), newAdmin.getPassword(), newAdmin.getRoles());

        when(mapper.dtoToUser(newAdminDto)).thenReturn(newAdmin);
        when(userRepository.save(newAdmin)).thenReturn(newAdmin);
        when(mapper.userToDto(newAdmin)).thenReturn(newAdminDto);

        UserDto result = adminService.create(newAdminDto);

        assertNotNull(result);
        assertEquals(newAdmin.getEmail(), result.getEmail());
        verify(userRepository).save(newAdmin);
        verify(mapper).dtoToUser(newAdminDto);
        verify(mapper).userToDto(newAdmin);
    }

    @Test
    void getByEmail_shouldReturnUserDto() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(mapper.userToDto(user)).thenReturn(userDto);

        UserDto result = adminService.getByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).findByEmail(user.getEmail());
        verify(mapper).userToDto(user);
    }

    @Test
    void deleteByEmail_shouldCallRepositoryDelete() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        adminService.deleteByEmail(user.getEmail());

        verify(userRepository).deleteByEmail(user.getEmail());
    }

    @Test
    void updateByEmail_shouldReturnUserDto() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDto updatedDto = new UserDto("new_test", "t@test.com", "test123", "ROLE_USER");
        when(mapper.userToDto(user)).thenReturn(updatedDto);
        when(userRepository.save(user)).thenReturn(user);

        UserDto result = adminService.updateByEmail(user.getEmail(), updatedDto);

        assertNotNull(result);
        assertEquals(updatedDto.getName(), result.getName());
        assertEquals(updatedDto.getEmail(), result.getEmail());
        verify(userRepository).save(user);
        verify(mapper).userToDto(user);
    }

    @Test
    void getAllUser_shouldReturnList() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(mapper.userToDto(user)).thenReturn(userDto);

        List<UserDto> result = adminService.getAllUser();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user.getEmail(), result.get(0).getEmail());
        verify(userRepository).findAll();
        verify(mapper).userToDto(user);
    }
}