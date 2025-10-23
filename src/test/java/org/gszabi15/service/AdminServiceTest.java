package org.gszabi15.service;

import org.gszabi15.model.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminServiceTest {
    @Autowired
    AdminService service;

    private static final UserDto testuser = new UserDto("test", "test@t.com", "test123", "");
    private static final UserDto adminuser = new UserDto("admin", "admin@example.com", "admin123", "ROLE_USER,ROLE_ADMIN");

    @BeforeEach
    void setUp() throws Exception {
        service.create(testuser);
    }

    @Test
    void create() throws Exception {
        UserDto newadmin = new UserDto("newadmin","newadmin@example.com", "newadmin123", "ROLE_USER,ROLE_ADMIN");
        service.create(newadmin);
    }

    @Test
    void getByEmail() throws Exception {
        service.getByEmail(testuser.getEmail());
    }

    @Test
    void deleteByEmail() throws Exception {
        service.deleteByEmail(testuser.getEmail());
    }

    @Test
    void updateByEmail() throws Exception {
        service.updateByEmail(testuser.getEmail(), testuser);
    }

    @Test
    void getAllUser() throws Exception {
        service.getAllUser();
    }
}