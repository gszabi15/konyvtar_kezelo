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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    private static final User user = new User(new UUID(123,123), "test", "test@t.com", "test123", "");
    private static final UserDto adminuser = new UserDto("admin", "admin@example.com", "admin123", "ROLE_USER,ROLE_ADMIN");


    @Test
    void create() {
        when(userRepository.save(user)).thenReturn(user);
        UserDto userDto = new UserDto(user.getName(), user.getEmail(), user.getPassword(), user.getRoles());
        when(mapper.dtoToUser(userDto)).thenReturn(user);
        String result = userService.create(userDto);

        assertNotNull(result);
        assertEquals("User created successfully.", result);
        verify(userRepository).save(user);
    }

    @Test
    void update() throws Exception {

    }

    @Test
    void delete_shouldReturnOk() {
        // Mock Authentication
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(user.getEmail());
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Mock repository
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // Act
        String result = userService.delete();

        // Assert
        assertEquals("User deleted successfully.", result);
        verify(userRepository).deleteByEmail(user.getEmail());
    }

    @Test
    void loadUserByUsername() throws Exception {

    }
}