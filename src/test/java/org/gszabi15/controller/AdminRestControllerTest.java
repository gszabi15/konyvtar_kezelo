package org.gszabi15.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gszabi15.model.dto.UserDto;
import org.gszabi15.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
class AdminRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    private static final UserDto testuser = new UserDto("test", "test@t.com", "test123", "");
    private static final UserDto adminuser = new UserDto("admin", "admin@example.com", "admin123", "ROLE_USER,ROLE_ADMIN");

    @BeforeEach
    void setUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testuser)))
                .andExpect(status().isOk())
                .andExpect(content().string("User created successfully."));
    }

    @Test
    void create() throws Exception {
        String token = jwtService.generateToken(adminuser.getEmail());
        UserDto dto = new UserDto("newadmin","newadmin@example.com", "newadmin123", "ROLE_USER,ROLE_ADMIN");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void getByEmail() throws Exception {
        String token = jwtService.generateToken(adminuser.getEmail());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/getByEmail/"+testuser.getEmail())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateByEmail() throws Exception {
        String token = jwtService.generateToken(adminuser.getEmail());
        UserDto newuser = new UserDto();
        newuser.setName("new_test");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/updateByEmail/"+testuser.getEmail())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newuser)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByEmail() throws Exception {
        String token = jwtService.generateToken(adminuser.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/deleteByEmail/"+testuser.getEmail())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllUser() throws Exception {
        String token = jwtService.generateToken(adminuser.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/getAllUser")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}