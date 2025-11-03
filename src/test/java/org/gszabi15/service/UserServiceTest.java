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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private EntityMapper mapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                UUID.randomUUID(),
                "test",
                "test@t.com",
                "test123",
                "ROLE_USER"
        );
    }

    private void mockCurrentUser() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(user.getEmail());
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    }

    @Test
    void create_shouldReturnOk() {
        when(userRepository.save(user)).thenReturn(user);
        UserDto userDto = new UserDto(user.getName(), user.getEmail(), user.getPassword(), user.getRoles());
        when(mapper.dtoToUser(userDto)).thenReturn(user);
        String result = userService.create(userDto);

        assertNotNull(result);
        assertEquals("User created successfully.", result);
        verify(userRepository).save(user);
    }

    @Test
    void update_shouldReturnOk() {
        mockCurrentUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDto userDto = new UserDto("updatedName", user.getEmail(), user.getPassword(), user.getRoles());

        when(userRepository.save(user)).thenReturn(user);
        String result = userService.update(userDto);

        assertEquals(userRepository.findById(user.getId()).get().getName(), userDto.getName());
        assertEquals("User updated successfully.", result);
        verify(userRepository).save(user);
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        mockCurrentUser();

        when(userRepository.existsById(user.getId())).thenReturn(true);

        String result = userService.delete();

        assertEquals("User deleted successfully.", result);
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());

        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        assertFalse(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

        verify(userRepository).findByEmail(user.getEmail());

    }
}