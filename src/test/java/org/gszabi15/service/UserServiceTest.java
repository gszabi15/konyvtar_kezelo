package org.gszabi15.service;

import org.gszabi15.model.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService service;

    private static final UserDto testuser = new UserDto("test", "test@t.com", "test123", "");
    private static final UserDto adminuser = new UserDto("admin", "admin@example.com", "admin123", "ROLE_USER,ROLE_ADMIN");


    @Test
    void create() throws Exception {
        service.create(testuser);
        Authentication auth = new UsernamePasswordAuthenticationToken(testuser.getEmail(), null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void update() throws Exception {
        create();
        service.update(testuser);
    }

    @Test
    void delete() throws Exception {
        create();
        service.delete();
    }

    @Test
    void loadUserByUsername() throws Exception {
        create();
        service.loadUserByUsername(testuser.getEmail());
    }
}